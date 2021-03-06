// Copyright 2018 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.skylarkbuildapi.apple;

import com.google.devtools.build.lib.skylarkbuildapi.FileApi;
import com.google.devtools.build.lib.skylarkbuildapi.core.ProviderApi;
import com.google.devtools.build.lib.skylarkbuildapi.core.StructApi;
import com.google.devtools.build.lib.skylarkinterface.Param;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.SkylarkConstructor;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;
import com.google.devtools.build.lib.syntax.EvalException;

/**
 * Interface for an info type containing information regarding multi-architecture Apple static
 * libraries.
 */
@SkylarkModule(
    name = "AppleStaticLibrary",
    category = SkylarkModuleCategory.PROVIDER,
    doc = "A provider containing information regarding multi-architecture Apple static libraries, "
        + "as is propagated by the apple_static_library rule."
)
public interface AppleStaticLibraryInfoApi extends StructApi {

  /** Skylark name for this interface. */
  public static final String SKYLARK_NAME = "AppleStaticLibrary";

  @SkylarkCallable(name = "archive",
      structField = true,
      doc = "The multi-arch archive (.a) output by apple_static_library."
  )
  public FileApi getMultiArchArchive();

  @SkylarkCallable(name = "objc",
      structField = true,
      doc = "A provider which contains information about the transitive dependencies linked into "
          + "the archive."
  )
  public ObjcProviderApi<?> getDepsObjcProvider();

  /**
   * Interface for the provider type for {@link AppleStaticLibraryInfoApi}.
   */
  public interface AppleStaticLibraryInfoProvider<FileApiT extends FileApi,
      ObjcProviderApiT extends ObjcProviderApi<?>> extends ProviderApi {

    @SkylarkCallable(
        name = SKYLARK_NAME,
        doc = "The <code>AppleStaticLibrary</code> constructor.",
        parameters = {
            @Param(
                name = "archive",
                type = FileApi.class,
                named = true,
                positional = false,
                doc = "Multi-architecture archive (.a) representing a static library"),
            @Param(
                name = "objc",
                type = ObjcProviderApi.class,
                named = true,
                positional = false,
                doc = "A provider which contains information about the transitive dependencies "
                    + "linked into the archive."),
        },
        selfCall = true)
    @SkylarkConstructor(objectType = AppleStaticLibraryInfoApi.class)
    public AppleStaticLibraryInfoApi appleStaticLibrary(
        FileApiT archive, ObjcProviderApiT objcProvider) throws EvalException;
  }
}
