package com.netanel.coupons.facades;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.JbeansException;
import com.netanel.coupons.exception.LoginException;

public interface CouponClientFacade {
	CouponClientFacade login(String name, char[] password, ClientType clientType) throws LoginException, DAOException;
}
