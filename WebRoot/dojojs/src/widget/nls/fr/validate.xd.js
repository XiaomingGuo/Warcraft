/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.widget.nls.fr.validate"]], definePackage:function (dojo) {
	dojo.provide("dojo.widget.nls.fr.validate");
	dojo.hostenv.xdLoadFlattenedBundle("dojo.widget", "validate", "fr", ({"rangeMessage":"* Cette valeur est hors limites.", "invalidMessage":"* La valeur saisie est incorrecte.", "missingMessage":"* Cette valeur est obligatoire."}));
}})