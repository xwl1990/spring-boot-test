package com.ck.util;

import java.util.Map;

import com.ck.common.GenericRequestDto;
import com.ck.common.GenericResponseDto;
import com.ck.cst.CommonCst;
import com.ck.exception.BusinessException;

/**
 * Description: 响应处理.
 *
 * @author: xieweili
 * @since: 2017年1月12日
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public final class RespdUtils {

    private RespdUtils() {
    }

    /**
     * @param resp
     * @return
     */
    public static GenericResponseDto success(Map<String, Object> resp) {
        GenericResponseDto respDto = new GenericResponseDto();
        respDto.setResultCode(CommonCst.RESPONSE_OK);
        respDto.putAll(resp);
        return respDto;
    }

    /**
     * @param resp
     * @return
     */
    public static GenericResponseDto success() {
        GenericResponseDto respDto = new GenericResponseDto();
        respDto.setResultCode(CommonCst.RESPONSE_OK);
        return respDto;
    }

    /**
     * 失败构建
     * 
     * @param resultCode
     * @return
     */
    public static GenericResponseDto failure(String resultCode) {
        GenericResponseDto respDto = new GenericResponseDto();
        respDto.setResultCode(resultCode);
        return respDto;
    }

    public static GenericResponseDto failure(String resultCode, String errorCode, String errorDesc) {
        GenericResponseDto respDto = new GenericResponseDto();
        respDto.setResultCode(resultCode);
        respDto.setErrorCode(errorCode);
        respDto.setErrorDesc(errorDesc);
        return respDto;
    }

    /**
     * 失败构建
     * 
     * @param resultCode
     * @return
     */
    public static GenericResponseDto failure(BusinessException e) {
        GenericResponseDto respDto = new GenericResponseDto();
        respDto.setResultCode(e.getResultCode());
        respDto.setErrorCode(e.getErrorCode());
        respDto.setErrorDesc(e.getErrorDesc());
        return respDto;
    }

    public static GenericRequestDto buildReqDto(Object reqModel) {
        GenericRequestDto req = new GenericRequestDto();
        Map<String, Object> reqMap = MapperUtils.toMap(MapperUtils.toJson(reqModel));
        req.putAll(reqMap);
        return req;
    }

}
