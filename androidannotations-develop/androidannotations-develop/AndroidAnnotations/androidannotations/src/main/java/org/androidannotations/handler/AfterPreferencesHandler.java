/**
 * Copyright (C) 2010-2015 eBusiness Information, Excilys Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.androidannotations.handler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.holder.HasPreferences;
import org.androidannotations.model.AnnotationElements;
import org.androidannotations.process.IsValid;

public class AfterPreferencesHandler extends BaseAnnotationHandler<HasPreferences> {

	public AfterPreferencesHandler(ProcessingEnvironment processingEnvironment) {
		super(AfterPreferences.class, processingEnvironment);
	}

	@Override
	public void validate(Element element, AnnotationElements validatedElements, IsValid valid) {
		validatorHelper.enclosingElementHasEActivityOrEFragment(element, validatedElements, valid);
		validatorHelper.enclosingElementExtendsPreferenceActivityOrPreferenceFragment(element, valid);

		ExecutableElement executableElement = (ExecutableElement) element;

		validatorHelper.returnTypeIsVoid(executableElement, valid);

		validatorHelper.isNotPrivate(element, valid);

		validatorHelper.doesntThrowException(executableElement, valid);

		validatorHelper.param.noParam().validate(executableElement, valid);
	}

	@Override
	public void process(Element element, HasPreferences holder) {
		String methodName = element.getSimpleName().toString();
		holder.getAddPreferencesFromResourceBlock().invoke(methodName);
	}
}
