package com.app.idea.net.token;

/**
 * Created by david on 16/11/24.
 */

public interface IGlobalManager {
    /**
     * Exit the login state.
     */
    void logout();

    void tokenRefresh(RefreshTokenResponse response);
}
