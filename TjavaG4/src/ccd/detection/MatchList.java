package ccd.detection;

import ccd.model.Sequence;

import java.util.ArrayList;
import java.util.List;

public class MatchList {
    public Sequence s_Sequence;
    public Sequence t_Sequence;
    public List<Integer> matchLS = new ArrayList<Integer>();
    public List<Integer> matchLT = new ArrayList<Integer>();
}
