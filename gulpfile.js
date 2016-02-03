var hbsp = require("hbsp");
var vdev = require("vdev");
var path = require("path");
var concat = require('gulp-concat');
var gulp = require("gulp");
var less = require("gulp-less");
var del = require("del");
var fs = require("fs");

var hbsPrecompile = hbsp.precompile;

var dbPrefix = "contactsserver";
var webappDir = "src/main/webapp/";
var sqlDir = "src/main/webapp/WEB-INF/sql/";


// Since we have quite a bit of web directories with less/ and tmpl/ rather to have too much redundancy 
// in the gulpfiles.js, we are going to create the gulp tasks programmatically. 

// List all of the web directory relative to webappDir that needs to be processed. 
// Each of those folders will follow the patter for "less/ to css/" and "tmpl/ to js/templates.js" processing.
var webDirNames = ["_base"];

// return: getBaseWebDir("common") would return "src/main/webapp/common/"
function getBaseWebDir(webDirName){
	var webDir = (webDirName === "_base")?"":webDirName + "/";
	return path.join(webappDir, webDir);
}

// param: webType = "less"|"tmpl"
// return: getWebDir("common","less") would return "src/main/webapp/common/less"
function getWebDir(webDirName, webType){    
	return path.join(getBaseWebDir(webDirName), webType, "/");
}

// return: getWebDirMatch("common","less") would return "src/main/webapp/common/less/*.less"
function getWebDirMatch(webDirName, webType){
	return path.join(getWebDir(webDirName, webType), "*." + webType);
}    

function getWebDirGulpTaskName(webDirName, webType){
	return webType + "-" + webDirName;
}

// Build the default gulp task list following the "webType-webDirName" 
var webDir, defaultTasks = ['clean'];
webDirNames.forEach(function(webDirName){
	defaultTasks.push("tmpl-" + webDirName);
	defaultTasks.push("less-" + webDirName);
});


gulp.task('default',defaultTasks);

// --------- WebDir Processing Gulp Tasks and Utilities --------- //

// Allow for realtime development by watching all the directory following the naming convention
gulp.task('watch', ['default'], function(){
	var webDirName;

	for (var i = 0; i < webDirNames.length; i++){
		webDirName = webDirNames[i];
		watchWebDir(webDirName, "less");
		watchWebDir(webDirName, "tmpl");
	}
});

// Convenient method to gulp.watch a webDir for a given webType and bind it 
// to the matching gulp task given the gulp task naming convention "webType-webDirName"
// param: webType = "less"|"tmpl"
function watchWebDir(webDirName, webType){
	var webDirMatch = getWebDirMatch(webDirName, webType);
	var gulpTaskName = getWebDirGulpTaskName(webDirName, webType);
	gulp.watch(webDirMatch, [gulpTaskName]);
}

// Clean the css folders to make sure no css left over
// Note: so far, we do not clean the tmpl templates.js as we have one file per webDir
gulp.task('clean', function(){
	var webDirName, baseWebDir, cssWebDir;


	for (var i = 0; i < webDirNames.length ; i ++){
		webDirName = webDirNames[i];
		baseWebDir = getBaseWebDir(webDirName);
		cssWebDir = path.join(baseWebDir, "css/");

		// make sure the directories exists (they might not in fresh clone)
		if (!fs.existsSync(cssWebDir)) {
			fs.mkdir(cssWebDir);
		}
		// delete the .css files (this makes sure we do not )
		del.sync(cssWebDir + "*.css");
	}
});

// create the gulp task dynamically from the list of webDirNames
webDirNames.forEach(function(webDirName){
	gulpTaskForWebDirName(webDirName,"tmpl");
	gulpTaskForWebDirName(webDirName,"less");
});

function gulpTaskForWebDirName(webDirName, webType){
	
	// will create something like "tmpl-_base"
	var gulpTaskName = getWebDirGulpTaskName(webDirName, webType);

	gulp.task(gulpTaskName, function() {
		var webDirMatch = getWebDirMatch(webDirName, webType);
		var baseWebDir = getBaseWebDir(webDirName);

		if (webType === "tmpl"){
			gulp.src(webDirMatch)
				.pipe(hbsPrecompile())
				.pipe(concat("templates.js"))
				.pipe(gulp.dest(path.join(baseWebDir,"/js/")));		
		} else if (webType === "less"){
			gulp.src(webDirMatch)
				.pipe(less())
				.pipe(gulp.dest(path.join(baseWebDir, "css/")));		
		} else {
			throw "cannot gulpTaskForWebDirName for unknown type " + webType;
		}
	});
}
// --------- /WebDir Processing Gulp Tasks and Utilities --------- //

// --------- Dev Utilities --------- //
gulp.task('recreateDb', function(){
	vdev.psql("postgres", null, "postgres", vdev.listSqlFiles(sqlDir,{to:0}));      
	vdev.psql(dbPrefix + "_user", null, dbPrefix + "_db", vdev.listSqlFiles(sqlDir,{from:1}));
});
// --------- /Dev Utilities --------- //
