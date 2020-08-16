package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pgeSize");
        String cidStr = req.getParameter("cid");

        String rname = req.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid = 0;
        if (cidStr !=null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }
        int pageSize = 0;
        if (pageSizeStr!=null &&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid,currentPage,pageSize,rname);
        writeValue(pb,resp);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(rid);
        writeValue(route,response);
    }
    public void isFavorite(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }

        boolean flag = favoriteService.isFavorite(rid,uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            return;
        }else{
            uid = user.getUid();
        }
        favoriteService.addFavorite(rid,uid);
    }
}
