package com.netanel.coupons.facades;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;

/**
 * Coupon Client Facade Interface.
 */
public interface CouponClientFacade {
	/**
	 * Coupon System Client Login.
	 * @param name a {@code String} client login name.
	 * @param password a {@code char[]} client password.
	 * @param clientType a {@code ClientType} Enum of the client type. 
	 * @return a {@code CouponClientFacade} implementation.
	 * @throws LoginException
	 * @throws DAOException
	 */
	CouponClientFacade login(String name, char[] password, ClientType clientType) throws LoginException, DAOException;
}
