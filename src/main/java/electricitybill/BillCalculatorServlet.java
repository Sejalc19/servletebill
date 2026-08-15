/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package electricitybill;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BillCalculatorServlet
 * ----------------------
 * Calculates an electricity bill using slab (tiered) pricing and forwards
 * the computed values to result.jsp for display.
 *
 * Tariff slabs:
 *   Units 1-50           : Rs. 3.50 / unit
 *   Units 51-150         : Rs. 4.00 / unit
 *   Units 151-250        : Rs. 5.20 / unit
 *   Units 251 and above  : Rs. 6.50 / unit
 *
 * @author magad
 */
@WebServlet(name = "BillCalculatorServlet", urlPatterns = {"/CalculateBillServlet"})
public class BillCalculatorServlet extends HttpServlet {

    private static final double SLAB1_RATE = 3.50; // first 50 units
    private static final double SLAB2_RATE = 4.00; // next 100 units (51-150)
    private static final double SLAB3_RATE = 5.20; // next 100 units (151-250)
    private static final double SLAB4_RATE = 6.50; // above 250 units

    private static final int SLAB1_LIMIT = 50;
    private static final int SLAB2_LIMIT = 150;
    private static final int SLAB3_LIMIT = 250;

    private static final double METER_RENT = 30.00;
    private static final double DUTY_PERCENT = 0.03; // 3% electricity duty

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String consumerName = request.getParameter("consumerName");
        String consumerNo = request.getParameter("consumerNo");
        String unitsParam = request.getParameter("units");

        String target;

        int units;
        try {
            if (unitsParam == null || unitsParam.trim().isEmpty()) {
                throw new NumberFormatException("empty");
            }
            units = Integer.parseInt(unitsParam.trim());
            if (units < 0) {
                throw new NumberFormatException("negative");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Please enter a valid, non-negative number of units.");
            target = "error.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(target);
            rd.forward(request, response);
            return;
        }

        if (consumerName == null || consumerName.trim().isEmpty()) {
            consumerName = "Consumer";
        }
        if (consumerNo == null || consumerNo.trim().isEmpty()) {
            consumerNo = "N/A";
        }

        // ---- Slab-wise breakdown ----
        int remaining = units;

        int slab1Units = Math.min(remaining, SLAB1_LIMIT);
        double slab1Amt = slab1Units * SLAB1_RATE;
        remaining -= slab1Units;

        int slab2Units = 0;
        double slab2Amt = 0;
        if (remaining > 0) {
            slab2Units = Math.min(remaining, SLAB2_LIMIT - SLAB1_LIMIT);
            slab2Amt = slab2Units * SLAB2_RATE;
            remaining -= slab2Units;
        }

        int slab3Units = 0;
        double slab3Amt = 0;
        if (remaining > 0) {
            slab3Units = Math.min(remaining, SLAB3_LIMIT - SLAB2_LIMIT);
            slab3Amt = slab3Units * SLAB3_RATE;
            remaining -= slab3Units;
        }

        int slab4Units = 0;
        double slab4Amt = 0;
        if (remaining > 0) {
            slab4Units = remaining;
            slab4Amt = slab4Units * SLAB4_RATE;
        }

        double energyCharges = round2(slab1Amt + slab2Amt + slab3Amt + slab4Amt);
        double electricityDuty = round2(energyCharges * DUTY_PERCENT);
        double grandTotal = round2(energyCharges + METER_RENT + electricityDuty);

        // ---- Push everything the JSP needs into the request scope ----
        request.setAttribute("consumerName", consumerName);
        request.setAttribute("consumerNo", consumerNo);
        request.setAttribute("units", units);

        request.setAttribute("slab1Units", slab1Units);
        request.setAttribute("slab1Amt", slab1Amt);
        request.setAttribute("slab2Units", slab2Units);
        request.setAttribute("slab2Amt", slab2Amt);
        request.setAttribute("slab3Units", slab3Units);
        request.setAttribute("slab3Amt", slab3Amt);
        request.setAttribute("slab4Units", slab4Units);
        request.setAttribute("slab4Amt", slab4Amt);

        request.setAttribute("energyCharges", energyCharges);
        request.setAttribute("meterRent", METER_RENT);
        request.setAttribute("electricityDuty", electricityDuty);
        request.setAttribute("grandTotal", grandTotal);

        // Pre-formatted (2 decimal place) strings so the JSP can stay
        // logic-free and doesn't need the JSTL fmt taglib.
        request.setAttribute("slab1AmtStr", fmt(slab1Amt));
        request.setAttribute("slab2AmtStr", fmt(slab2Amt));
        request.setAttribute("slab3AmtStr", fmt(slab3Amt));
        request.setAttribute("slab4AmtStr", fmt(slab4Amt));
        request.setAttribute("energyChargesStr", fmt(energyCharges));
        request.setAttribute("meterRentStr", fmt(METER_RENT));
        request.setAttribute("electricityDutyStr", fmt(electricityDuty));
        request.setAttribute("grandTotalStr", fmt(grandTotal));

        target = "result.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(target);
        rd.forward(request, response);
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String fmt(double value) {
        return String.format("%.2f", value);
    }

    @Override
    public String getServletInfo() {
        return "Calculates electricity bills using slab-wise tariff rates.";
    }
}