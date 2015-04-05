package com.plugin.commons.service;

import com.plugin.commons.ComApp;
import com.plugin.commons.model.CommonModel;
import com.plugin.commons.model.RspResultModel;

public class PaiKeBoKeServiceImpl implements PaiKeBoKeService {

	@Override
	public RspResultModel pushPhotos(CommonModel cmmodel) {
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().pushPhotos(cmmodel);
		return rsp;
	}

	@Override
	public RspResultModel pushVideo(CommonModel cmmodel) {
		RspResultModel rsp = null;
		rsp = ComApp.getInstance().getApi().pushVideo(cmmodel);
		return rsp;
	}

}
