package org.itmo.servletjsfserver.model;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

import java.io.Serializable;
import java.util.Map;


@Named("coordinateBean")
@SessionScoped
public class CoordinateBean implements Serializable {
    private double x = 0;
    private double y = 0;
    private double r = 1;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String submit() {
        return "result"; // Возвращаем имя страницы для перехода после отправки формы
    }


    public void handleRChange(ValueChangeEvent event) {
        System.out.println((Double) event.getOldValue());
        System.out.println((Double) event.getNewValue());
        if ((Double) event.getNewValue() <= 0) {
            this.r = (Double) event.getNewValue();
        }
        else
            this.r = (Double) event.getOldValue();
    }

    public void handleCanvasClick() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        double clickX = Double.parseDouble(params.get("x"));
        double clickY = Double.parseDouble(params.get("y"));

        // Обработка координат клика
        System.out.println("Canvas click at: (" + params.get("x") + ", " + params.get("y") + ")");
    }
}
