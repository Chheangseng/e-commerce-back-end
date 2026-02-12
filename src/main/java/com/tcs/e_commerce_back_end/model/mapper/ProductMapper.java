package com.tcs.e_commerce_back_end.model.mapper;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProduct;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductInit;
import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.model.entity.product.ProductInventory;
import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;

public class ProductMapper {
  public static void addItemToEntity(DtoProduct dtoProduct, Product product) {
    dtoProduct
        .getProductInventories()
        .forEach(
            dtoProductInventory -> {
              var entityItem = new ProductInventory();
              if (Objects.nonNull(dtoProduct.getId()))
                entityItem.setId(dtoProductInventory.getId());
              entityItem.setProduct(product);
              entityItem.setSize(dtoProductInventory.getSize());
              entityItem.setColor(dtoProductInventory.getColor());
              entityItem.setStock(dtoProductInventory.getStock());
              entityItem.setSellPrice(BigDecimal.valueOf(dtoProduct.getSellPrice()));
              entityItem.setPrice(BigDecimal.valueOf(dtoProduct.getPrice()));
              product.getProductInventories().add(entityItem);
            });
  }

  public static Product dtoProductToEntity(DtoProduct dtoProduct, Product product) {
    product.setId(dtoProduct.getId());
    product.setName(dtoProduct.getProductName());
    product.setDescription(dtoProduct.getDescription());
    product.setPrice(BigDecimal.valueOf(dtoProduct.getPrice()));
    product.setSellPrice(BigDecimal.valueOf(dtoProduct.getSellPrice()));
    if (dtoProduct.getProductInventories().isEmpty()) {
      throw new ApiExceptionStatusException("Inventory is require", 400);
    }
    ProductMapper.addItemToEntity(dtoProduct, product);
    product.setAverageRate(ObjectUtils.getIfNull(product.getAverageRate(), 0d));
    return product;
  }

  public static Product dtoProductInitToEntity(DtoProductInit dtoProduct) {
    var product = new Product();
    product.setName(dtoProduct.getProductName());
    product.setDescription(dtoProduct.getDescription());
    product.setPrice(BigDecimal.valueOf(dtoProduct.getPrice()));
    product.setSellPrice(BigDecimal.valueOf(dtoProduct.getSellPrice()));
    ProductMapper.addItemInitToEntity(dtoProduct, product);
    product.setAverageRate(ObjectUtils.getIfNull(product.getAverageRate(), 0d));
    return product;
  }

  private static void addItemInitToEntity(DtoProductInit dtoProduct, Product product) {
    dtoProduct
        .removeInventoriesId()
        .forEach(
            dtoProductInventory -> {
              var entityItem = new ProductInventory();
              entityItem.addProduct(product);
              entityItem.setSize(dtoProductInventory.getSize());
              entityItem.setColor(dtoProductInventory.getColor());
              entityItem.setStock(dtoProductInventory.getStock());
              entityItem.setSellPrice(BigDecimal.valueOf(dtoProduct.getSellPrice()));
              entityItem.setPrice(BigDecimal.valueOf(dtoProduct.getPrice()));
            });
  }
}
