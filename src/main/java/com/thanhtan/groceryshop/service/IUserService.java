package com.thanhtan.groceryshop.service;

import com.thanhtan.groceryshop.dto.request.UserRequest;
import com.thanhtan.groceryshop.dto.response.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest request);
}
