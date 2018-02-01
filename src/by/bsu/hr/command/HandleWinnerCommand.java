package by.bsu.hr.command;

import by.bsu.hr.entity.Winner;
import by.bsu.hr.logic.GoWinnersLogic;
import by.bsu.hr.logic.HandleWinnerLogic;
import by.bsu.hr.logic.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class HandleWinnerCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        LocaleResourceBundle.ResourceBundleEnum rb= (LocaleResourceBundle.ResourceBundleEnum) session.getAttribute("rb");
        int user= Integer.parseInt(request.getParameter("user"));
        int vacancy= Integer.parseInt(request.getParameter("vacancy"));
        List<Winner> resList=null;
        try {
            HandleWinnerLogic.handleWinner(user,vacancy);
            resList= GoWinnersLogic.getWinners();
        } catch (LogicException e) {
            e.printStackTrace();
        }

        request.setAttribute("winList",resList);
        SetAttributes.setAttributesHRWinnersPage(rb,request);
        return PageConstant.HR_WINNERS_PAGE;
    }
}