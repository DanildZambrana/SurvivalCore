/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package net.wezland.survivalcore.modules.googleauth.utils;

import java.util.ArrayList;
import java.util.List;

public class GAuthManager {
    private List<String> authLocked;

    {
        this.authLocked = new ArrayList<>();
    }


    public List<String> getAuthLocked() {
        return authLocked;
    }
}
