package com.netanel.coupons.clients;

import com.netanel.coupons.crypt.Password;

public interface CouponClientFacade {
	CouponClientFacade login(String name, Password password, ClientType clientType);
}
