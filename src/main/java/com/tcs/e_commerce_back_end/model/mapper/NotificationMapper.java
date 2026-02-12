package com.tcs.e_commerce_back_end.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.notification.DtoNotification;
import com.tcs.e_commerce_back_end.model.entity.NotificationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationMapper {
    public static List<DtoNotification> mapEntityToDto(List<NotificationEntity> entities, ObjectMapper objectMapper) {
        try {
            List<DtoNotification> response = new ArrayList<>();
            for (var item : entities) {
                var dto = new DtoNotification(item, item.getUserAccount());
                Map<String, Object> map =
                        objectMapper.readValue(
                                item.getJsonObject(), new TypeReference<Map<String, Object>>() {});
                dto.setJsonObject(map);
                response.add(dto);
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new ApiExceptionStatusException(
                    "Something went wrong with convert string to json", 400);
        }
    }
}
