/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.i18n.currency.nls.ja.USD"]], definePackage:function (dojo) {
	dojo.provide("dojo.i18n.currency.nls.ja.USD");
	dojo.hostenv.xdLoadFlattenedBundle("dojo.i18n.currency", "USD", "ja", ({"displayName":"\u7c73\u30c9\u30eb", "symbol":"$"}));
}})