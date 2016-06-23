/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.widget.nls.validate"]], definePackage:function (dojo) {
	dojo.provide("dojo.widget.nls.validate");
	dojo.hostenv.xdLoadFlattenedBundle("dojo.widget", "validate", "", ({"rangeMessage":"* This value is out of range.", "invalidMessage":"* The value entered is not valid.", "missingMessage":"* This value is required."}));
}})