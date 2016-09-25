/*
   Copyright 2008 Simon Mieth

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.kabeja.ui.model;

import javax.swing.tree.TreeNode;

import org.kabeja.parser.Parser;


public class ParserTreeNode extends AbstractProcessingTreeNode {
    protected Parser parser;

    public ParserTreeNode(TreeNode parent, Parser parser) {
        super(parent, parser.getName());

        this.parser = parser;
    }

    @Override
    protected String getLabel() {
        // TODO Auto-generated method stub
        return parser.getName();
    }

    @Override
    protected void initializeChildren() {
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
