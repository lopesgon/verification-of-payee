package com.pictet.vop.v1.dtos;

import java.util.List;

import com.pictet.vop.v1.types.PayeeType;
import com.pictet.vop.v1.types.ResultEnum;

public record PayeeMatchResultDTO(ResultEnum result, List<PayeeType> payees) {

}
