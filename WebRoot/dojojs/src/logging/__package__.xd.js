/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["kwCompoundRequire", {common:[["dojo.logging.Logger", false, false]], rhino:["dojo.logging.RhinoLogger"]}], ["provide", "dojo.logging.*"]], definePackage:function (dojo) {
	dojo.kwCompoundRequire({common:[["dojo.logging.Logger", false, false]], rhino:["dojo.logging.RhinoLogger"]});
	dojo.provide("dojo.logging.*");
}});

