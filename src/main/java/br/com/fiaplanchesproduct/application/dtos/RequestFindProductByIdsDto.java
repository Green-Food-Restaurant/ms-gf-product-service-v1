package br.com.fiaplanchesproduct.application.dtos;

import java.util.List;

public record RequestFindProductByIdsDto(
        List<Long> productIds
)
{}
