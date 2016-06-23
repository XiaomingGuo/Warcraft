/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.animation"], ["require", "dojo.animation.Animation"]], definePackage:function (dojo) {
	dojo.provide("dojo.animation");
	dojo.require("dojo.animation.Animation");
	dojo.deprecated("dojo.animation is slated for removal in 0.5; use dojo.lfx instead.", "0.5");
}});

