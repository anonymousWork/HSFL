/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.math.linear;

import java.io.Serializable;

/**
 * An interface to classes that implement an algorithm to calculate the 
 * eigen decomposition of a real symmetric matrix.
 * <p>The eigen decomposition of matrix A is a set of two matrices:
 * V and D such that A = V &times; D &times; V<sup>T</sup>.
 * A, V and D are all m &times; m matrices.</p>
 * <p>This interface is similar in spirit to the <code>EigenvalueDecomposition</code>
 * class from the now defunct <a href="http://math.nist.gov/javanumerics/jama/">JAMA</a>
 * library, with the following changes:</p>
 * <ul>
 *   <li>a {@link #getVT() getVt} method has been added,</li>
 *   <li>a {@link #getEigenvalue(int) getEigenvalue} method to pick up a single
 *   eigenvalue has been added,</li>
 *   <li>a {@link #getEigenvector(int) getEigenvector} method to pick up a single
 *   eigenvector has been added,</li>
 *   <li>the <code>getRealEigenvalues</code> method has been renamed as {@link
 *   #getEigenValues() getEigenValues},</li>
 *   <li>the <code>getImagEigenvalues</code> method has been removed</li>
 * </ul>
 * @see <a href="http://mathworld.wolfram.com/EigenDecomposition.html">MathWorld</a>
 * @see <a href="http://en.wikipedia.org/wiki/Eigendecomposition_of_a_matrix">Wikipedia</a>
 * @version $Revision$ $Date$
 * @since 2.0
 */
public interface EigenDecomposition extends Serializable {

    /**
     * Returns the matrix V of the decomposition. 
     * <p>V is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * <p>The columns of V are the eigenvectors of the original matrix.</p>
     * @return the V matrix
     */
    RealMatrix getV();

    /**
     * Returns the diagonal matrix D of the decomposition. 
     * <p>D is a diagonal matrix.</p>
     * <p>The values on the diagonal are the eigenvalues of the original matrix.</p>
     * @return the D matrix
     * @see #getEigenValues()
     */
    RealMatrix getD();

    /**
     * Returns the transpose of the matrix V of the decomposition. 
     * <p>V is an orthogonal matrix, i.e. its transpose is also its inverse.</p>
     * <p>The columns of V are the eigenvectors of the original matrix.</p>
     * @return the transpose of the V matrix
     */
    RealMatrix getVT();

    /**
     * Returns a copy of the eigenvalues of the original matrix.
     * @return a copy of the eigenvalues of the original matrix
     * @see #getD()
     */
    double[] getEigenvalues();

    /**
     * Returns the i<sup>th</sup> eigenvalue of the original matrix.
     * @param i index of the eigenvalue (counting from 0)
     * @return i<sup>th</sup> eigenvalue of the original matrix
     * @see #getD()
     */
    double getEigenvalue(int i);

    /**
     * Returns a copy of the i<sup>th</sup> eigenvector of the original matrix.
     * @param i index of the eigenvector (counting from 0)
     * @return copy of the i<sup>th</sup> eigenvector of the original matrix
     * @see #getD()
     */
    RealVector getEigenvector(int i);

}
