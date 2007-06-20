/*
 * Copyright 2000-2007 JetBrains s.r.o.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.plugins.groovy.findUsages;

import com.intellij.usages.rules.UsageGroupingRule;
import com.intellij.usages.rules.PsiElementUsage;
import com.intellij.usages.UsageGroup;
import com.intellij.usages.Usage;
import com.intellij.usages.UsageView;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.psi.PsiElement;

import javax.swing.*;

import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression;

/**
 * @author ven
 */
public class LateBoundUsageGroupingRule implements UsageGroupingRule {
  class LateBoundGroup implements UsageGroup {

    public Icon getIcon(boolean isOpen) {
      return null;
    }

    public String getText(UsageView view) {
      return "Dynamically typed usages";
    }

    public FileStatus getFileStatus() {
      return null;
    }

    public boolean isValid() {
      return true;
    }

    public void update() {
    }

    public int compareTo(UsageGroup usageGroup) {
      return getText(null).compareTo(usageGroup.getText(null));
    }

    public void navigate(boolean b) {
    }

    public boolean canNavigate() {
      return false;
    }

    public boolean canNavigateToSource() {
      return false;
    }
  }

  public UsageGroup groupUsage(Usage usage) {
    if (usage instanceof PsiElementUsage) {
      final PsiElement element = ((PsiElementUsage) usage).getElement();
      if (element instanceof GrReferenceExpression && ((GrReferenceExpression) element).resolve() == null) {
        return new LateBoundGroup();
      }
    }

    return null;
  }
}
