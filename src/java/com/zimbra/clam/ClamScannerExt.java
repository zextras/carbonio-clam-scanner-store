// SPDX-FileCopyrightText: 2022 Synacor, Inc.
// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: GPL-2.0-only

package com.zimbra.clam;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Log;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.extension.ZimbraExtension;
import com.zimbra.cs.service.mail.UploadScanner;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

public class ClamScannerExt implements ZimbraExtension {

  private static final Log LOG = ZimbraLog.extensions;
  private final List<ClamScanner> clamScannerList = new LinkedList<>();

  @Override
  public synchronized void init() {

    try {
      ClamScannerConfig mConfig = new ClamScannerConfig();

      if (!mConfig.getEnabled()) {
        LOG.info("attachment scan is disabled");
        return;
      }

      String[] urls = mConfig.getURL();
      for (String s : urls) {
        ClamScanner clamScanner = new ClamScanner();
        clamScanner.setURL(s);
        UploadScanner.registerScanner(clamScanner);
        clamScannerList.add(clamScanner);
      }
    } catch (ServiceException | MalformedURLException e) {
      LOG.error("error creating scanner", e);
    }
  }

  @Override
  public void destroy() {
    for (ClamScanner clamScanner : clamScannerList) {
      UploadScanner.unregisterScanner(clamScanner);
    }
  }

  @Override
  public String getName() {
    return "clamscanner";
  }
}
