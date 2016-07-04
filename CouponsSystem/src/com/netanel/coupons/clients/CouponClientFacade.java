package com.netanel.coupons.clients;

import com.netanel.coupons.exception.LoginException;

public interface CouponClientFacade {
	CouponClientFacade login(String name, char[] password, ClientType clientType) throws LoginException;
}
