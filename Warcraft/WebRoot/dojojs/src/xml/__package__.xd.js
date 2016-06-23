/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["require", "dojo.xml.Parse"], ["kwCompoundRequire", {common:["dojo.dom"], browser:["dojo.html.*"], dashboard:["dojo.html.*"]}], ["provide", "dojo.xml.*"]], definePackage:function (dojo) {
	dojo.require("dojo.xml.Parse");
	dojo.kwCompoundRequire({common:["dojo.dom"], browser:["dojo.html.*"], dashboard:["dojo.html.*"]});
	dojo.provide("dojo.xml.*");
}});

