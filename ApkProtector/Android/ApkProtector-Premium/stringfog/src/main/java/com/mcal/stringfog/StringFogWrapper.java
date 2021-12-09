/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mcal.stringfog;

import com.mcal.stringfog.IStringFog;

/**
 * A wrapper for the real implementation of fogs.
 *
 * @author Megatron King
 * @since 2018/9/20 16:14
 */
public final class StringFogWrapper implements IStringFog {

    private IStringFog mStringFogImpl;

    public StringFogWrapper(String impl) {
        try {
            mStringFogImpl = (IStringFog) Class.forName(impl).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid stringfog implementation: " + impl);
        }
    }

    @Override
    public String encrypt(String data, String key) {
        return mStringFogImpl == null ? data : mStringFogImpl.encrypt(data, key);
    }

    @Override
    public String decrypt(String data, String key) {
        return mStringFogImpl == null ? data : mStringFogImpl.decrypt(data, key);
    }

    @Override
    public boolean overflow(String data, String key) {
        return mStringFogImpl != null && mStringFogImpl.overflow(data, key);
    }

}
