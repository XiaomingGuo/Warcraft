/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/



dojo.hostenv.packageLoaded({depends:[["provide", "dojo.rpc.JotService"], ["require", "dojo.rpc.RpcService"], ["require", "dojo.rpc.JsonService"], ["require", "dojo.json"]], definePackage:function (dojo) {
	dojo.provide("dojo.rpc.JotService");
	dojo.require("dojo.rpc.RpcService");
	dojo.require("dojo.rpc.JsonService");
	dojo.require("dojo.json");
	dojo.rpc.JotService = function () {
		this.serviceUrl = "/_/jsonrpc";
	};
	dojo.inherits(dojo.rpc.JotService, dojo.rpc.JsonService);
	dojo.lang.extend(dojo.rpc.JotService, {bind:function (method, parameters, deferredRequestHandler, url) {
		dojo.io.bind({url:url || this.serviceUrl, content:{json:this.createRequest(method, parameters)}, method:"POST", mimetype:"text/json", load:this.resultCallback(deferredRequestHandler), error:this.errorCallback(deferredRequestHandler), preventCache:true});
	}, createRequest:function (method, params) {
		var req = {"params":params, "method":method, "id":this.lastSubmissionId++};
		return dojo.json.serialize(req);
	}});
}});

