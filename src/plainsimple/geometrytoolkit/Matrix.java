package plainsimple.geometrytoolkit;

import c10n.C10N;

import java.util.ArrayList;

public class Matrix {
    /* stores data in rows */
    private ArrayList<RowVector> rows = new ArrayList<>();
    /* number of elements per row */
    private int row_size;
    /* sets row size */
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    public Matrix(int size) { row_size = size; }
    public Matrix(ArrayList<RowVector> rows) { this.rows = rows; }

    /* sets element at index (row, col) */
    public void setElement(int row, int col, double new_value) throws IndexOutOfBoundsException {
        if(row > rows() || col > columns())
            throw new IndexOutOfBoundsException
                    (msg.index_out_of_bounds(msg.matrix(), row + msg.comma() + col));
        else
            rows.get(row).set(col, new_value);
    }
    /* sets row at index */
    public void setRow(int row_index, RowVector new_row) throws IndexOutOfBoundsException {
        try{
            rows.set(row_index, new_row);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* returns element at index (row, col) */
    public double get(int row, int col) throws IndexOutOfBoundsException {
        if(row > rows() || col > columns())
            throw new IndexOutOfBoundsException
                    (msg.index_out_of_bounds(msg.matrix(), row + msg.comma() + col));
        else
            return rows.get(row).get(col);
    }
    /* returns row vector at index */
    public RowVector getRow(int row_index) throws IndexOutOfBoundsException {
        try{
            return rows.get(row_index);
        } catch(IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* adds row to matrix */
    public void addRow(RowVector new_row) {
        rows.add(new_row);
    }
    /* matrix addition */
    public Matrix add(Matrix m) throws IndexOutOfBoundsException {
        if(rows() != m.rows() || columns() != m.columns())
            throw new IndexOutOfBoundsException(msg.matrices_size_error(msg.added()));
        Matrix result = new Matrix(columns());
        for(int i = 0; i < rows(); i++) {
            RowVector row_sum = new RowVector();
            for(int j = 0; j < columns(); j++) {
                row_sum.addElement(get(i, j) + m.get(i, j));
            }
            result.addRow(row_sum);
        }
        return result;
    }
    /* matrix multiplication */
    public Matrix multiply(Matrix m) throws IndexOutOfBoundsException {
        if(rows() != m.columns())
            throw new IndexOutOfBoundsException(msg.matrices_size_error(msg.multiplied()));
        Matrix result = new Matrix(m.columns());
        for(int i = 0; i < rows(); i++) {
            RowVector row_multiple = new RowVector();
            for(int j = 0; j < columns(); j++) {
                double d = 0.0;
                for(int k = 0; k < columns(); k++)
                    d += get(i, k) * m.get(k, i);
                row_multiple.addElement(d);
            }
            result.addRow(row_multiple);
        }
        return result;
    }
    /* returns inverse of matrix using Gaussian Elimination */
    public Matrix inverse() throws IndexOutOfBoundsException {
        if(rows() != columns()) /* must be square */
            throw new IndexOutOfBoundsException(msg.matrices_size_error(msg.inverted()));
        Matrix combined_matrix = merge(identity(rows()));
        Matrix reduced_matrix = combined_matrix.reduce();
        Matrix result = new Matrix(columns());
        for(int i = 0; i < rows(); i++) {
            RowVector row = new RowVector();
            for(int j = 0; j < columns(); j++) {
                row.addElement(reduced_matrix.get(i, j));
            }
        }
        return result;
    }
    /* swaps two rows in a matrix */
    public Matrix swapRows(int row_1, int row_2) throws IndexOutOfBoundsException {
        Matrix result = new Matrix(columns());
        try {
            RowVector stored_row = rows.get(row_1);
            rows.set(row_1, rows.get(row_2));
            rows.set(row_2, stored_row);
            return new Matrix(rows);
        } catch(IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* row_1 = row_1 + multiple * row_2 */
    public Matrix addRowMultiple(int row_1, double multiple, int row_2) throws IndexOutOfBoundsException {
        try {
            rows.set(row_1, rows.get(row_1).add(rows.get(row_2).multiply(multiple)));
            return new Matrix(rows);
        } catch(IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* row = multiple * row */
    public Matrix multiplyRow(int row, double multiple) throws IndexOutOfBoundsException {
        try {
            rows.set(row, rows.get(row).multiply(multiple));
            return new Matrix(rows);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }
    /* reduces reduced matrix */
    public Matrix reduce() {
        Matrix result = new Matrix(rows);
        for(int i = 0; i < result.columns() / 2; i++) {
            for(int j = 0; j < result.rows(); j++) {
                if(i == j)
                    result.multiplyRow(j, 1 / rows.get(j).get(i));
                else
                    result.setRow(j, rows.get(j).subtract(rows.get(j).multiply(rows.get(i).get(j))));
            }
        }
        return result;
    }
    /* returns the identity matrix of specified size */
    public Matrix identity(int size) {
        Matrix identity = new Matrix(size);
        for(int i = 0; i < size; i++) {
            RowVector next_row = new RowVector(size);
            for(int j = 0; j < size; j++)
                if(i == j)
                    next_row.set(i, 1);
                else
                    next_row.set(i, 0);
            identity.addRow(next_row);
        }
        return identity;
    }
    /* merges two matrices */
    public Matrix merge(Matrix m) throws IndexOutOfBoundsException {
        if(rows() != m.rows())
            throw new IndexOutOfBoundsException(msg.matrices_size_error(msg.merged()));
        Matrix result = new Matrix(columns() + m.columns());
        for(int i = 0; i < rows(); i++) {
            result.addRow(rows.get(i).merge(m.getRow(i)));
        }
        return result;
    }
    /* returns number of rows in matrix */
    public int rows() {
        return row_size;
    }
    /* returns number of columns in matrix */
    public int columns() {
        return rows.get(0).size();
    }
    /* returns whether matrices have the same dimensions */
    public boolean sameSize(Matrix m) {
        return (rows() == m.rows() && columns() == m.columns());
    }
}
