/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abal.arsipsuratkalawat;

import com.thowo.jmjavaframework.JMDate;

/**
 *
 * @author jimi
 */
public interface IFilter {
    void filterSM(JMDate tglSuratFrom, JMDate tglSuratTo, JMDate tglTerimaFrom, JMDate tglTerimaTo, int tembusanMode);
    void filterSK(JMDate tglSuratFrom, JMDate tglSuratTo, JMDate tglKirimFrom, JMDate tglKirimTo);
}
