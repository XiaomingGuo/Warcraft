/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["require", "dojo.cal.iCalendar"]], definePackage:function (dojo) {
	dojo.require("dojo.cal.iCalendar");
	dojo.deprecated("dojo.icalendar", "use dojo.cal.iCalendar isntead", "0.5");
}});
