/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["require", "dojo.validate"], ["kwCompoundRequire", {common:["dojo.validate.check", "dojo.validate.datetime", "dojo.validate.de", "dojo.validate.jp", "dojo.validate.us", "dojo.validate.web"]}], ["provide", "dojo.validate.*"]], definePackage:function (dojo) {
	dojo.require("dojo.validate");
	dojo.kwCompoundRequire({common:["dojo.validate.check", "dojo.validate.datetime", "dojo.validate.de", "dojo.validate.jp", "dojo.validate.us", "dojo.validate.web"]});
	dojo.provide("dojo.validate.*");
}});

