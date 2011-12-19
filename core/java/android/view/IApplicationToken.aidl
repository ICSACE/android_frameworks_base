/* //device/java/android/android/view/IApplicationToken.aidl
**
** Copyright 2007, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License"); 
** you may not use this file except in compliance with the License. 
** You may obtain a copy of the License at 
**
**     http://www.apache.org/licenses/LICENSE-2.0 
**
** Unless required by applicable law or agreed to in writing, software 
** distributed under the License is distributed on an "AS IS" BASIS, 
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
** See the License for the specific language governing permissions and 
** limitations under the License.
*/

package android.view;

/** {@hide} */
interface IApplicationToken
{
<<<<<<< HEAD
    void windowsDrawn();
=======
>>>>>>> e3fc4d0ba9f68910f3a9cbecf266073bd28e1f9e
    void windowsVisible();
    void windowsGone();
    boolean keyDispatchingTimedOut();
    long getKeyDispatchingTimeout();
}

