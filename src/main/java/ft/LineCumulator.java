package ft;

public abstract class LineCumulator {

    /**
     * Cumulates <i>parentLine</i> to <i>line</i> taking the value of the
     * highest parent.
     * @param parentLine
     * @param line
     */
    public static void cumulate(int[] parentLine, int[] line) {
        int[] newPl = getParentLineWithZeroValuesForFirstAndLast(parentLine);
        for (int i=0; i<line.length; i++) {
            line[i] = line[i] + getValueOfHighestParent(newPl, i);
        }
    }

    private static int getValueOfHighestParent(int[] newPl, int i) {
        return Math.max(newPl[i], newPl[i + 1]);
    }

    private static int[] getParentLineWithZeroValuesForFirstAndLast(int[] parentLine) {
        int[] l = new int[parentLine.length + 2];
        System.arraycopy(parentLine, 0, l, 1, parentLine.length);
        l[0] = 0;
        l[l.length - 1] = 0;
        return l;
    }
}
