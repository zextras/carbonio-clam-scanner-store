// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.clam;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Config;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;

public class ClamScannerConfig {

    private final boolean mEnabled;
    
    private final String[] mURL;
    
    public ClamScannerConfig() throws ServiceException {
        Server serverConfig = Provisioning.getInstance().getLocalServer();
        mEnabled = serverConfig.getBooleanAttr(Provisioning.A_zimbraAttachmentsScanEnabled, false);
        mURL = serverConfig.getAttachmentsScanURL();
    }

    public boolean getEnabled() {
        return mEnabled;
    }
    
    public String[] getURL() {
        return mURL;
    }
}
