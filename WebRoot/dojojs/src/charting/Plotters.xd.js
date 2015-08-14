/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.charting.Plotters"], ["requireIf", dojo.render.svg.capable, "dojo.charting.svg.Plotters"], ["requireIf", dojo.render.vml.capable, "dojo.charting.vml.Plotters"]], definePackage:function (dojo) {
	dojo.provide("dojo.charting.Plotters");
	dojo.requireIf(dojo.render.svg.capable, "dojo.charting.svg.Plotters");
	dojo.requireIf(dojo.render.vml.capable, "dojo.charting.vml.Plotters");
}});

