package controller;

import domain.GraduateProjectStatus;

import service.DepartmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import service.GraduateProjectStatusService;

import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/graduateProjectStatus.ctl")
public class GraduateProjectStatusController extends HttpServlet {
    /**
     * 方法-功能
     * put 修改
     * post 添加
     * delete 删除
     * get 查找
     */
    //POST 49.235.219.168:8080/bysj/graduateProjectStatus.ctl
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String graduateProjectStatus_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProjectStatus对象
       GraduateProjectStatus graduateProjectStatusToAdd = JSON.parseObject(graduateProjectStatus_json, GraduateProjectStatus.class);
        System.out.println(graduateProjectStatusToAdd);
        //创建JSON对象
        JSONObject resp = new JSONObject();
        try {
            //增加加graduateProjectStatus对象
           GraduateProjectStatusService.getInstance().add(graduateProjectStatusToAdd);
            //加入数据信息
            resp.put("message", "添加成功");
        }catch (SQLException e){
            e.printStackTrace();
            resp.put("message", "数据库操作异常");
        }catch (Exception e) {
            e.printStackTrace();
            resp.put("message", "网络异常");
        }
        //响应
        response.getWriter().println(resp);
    }
    //DELETE 49.235.219.168:8080/bysj/graduateProjectStatus.ctl
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //创建JSON对象
        JSONObject resp = new JSONObject();
        try{
            //到数据库表中删除
            DepartmentService.getInstance().delete(id);
            //加入数据信息
            resp.put("message", "删除成功");
        }catch (SQLException e){
            e.printStackTrace();
            resp.put("message", "数据库操作异常");
        }catch (Exception e){
            e.printStackTrace();
            resp.put("message", "网络异常");
        }
        //响应
        response.getWriter().println(resp);
    }
    //PUT 49.235.219.168:8080/bysj/graduateProjectStatus.ctl
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String graduateProjectStatus_json = JSONUtil.getJSON(request);
        //将JSON字串解析为graduateProjectStatus对象
       GraduateProjectStatus graduateProjectStatusToAdd = JSON.parseObject(graduateProjectStatus_json,GraduateProjectStatus.class);
        //创建JSON对象
        JSONObject resp = new JSONObject();
        try{
            //增加加graduateProjectStatus对象
           GraduateProjectStatusService.getInstance().update(graduateProjectStatusToAdd);
            //加入数据信息
            resp.put("message", "修改成功");
        }catch (SQLException e){
            e.printStackTrace();
            resp.put("message", "数据库操作异常");
        }catch (Exception e){
            e.printStackTrace();
            resp.put("message", "网络异常");
        }
        //响应
        response.getWriter().println(resp);
    }
    //GET 49.235.219.168:8080/bysj/graduateProjectStatus.ctl
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有对象，否则响应id指定的对象
            if (id_str == null) {
                responseGraduateProjectStatuss(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseGraduateProjectStatus(id, response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    //响应一个对象
    private void responseGraduateProjectStatus(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        //根据id查找
       GraduateProjectStatus graduateProjectStatus =GraduateProjectStatusService.getInstance().find(id);
        String graduateProjectStatus_json = JSON.toJSONString(graduateProjectStatus);
        //控制台打印结果
        System.out.println(graduateProjectStatus_json);
        //浏览器展示结果
        response.getWriter().println(graduateProjectStatus_json);
    }
    //响应所有对象
    private void responseGraduateProjectStatuss(HttpServletResponse response)
            throws ServletException, IOException,SQLException {
        //获得所有
        Collection<GraduateProjectStatus>graduateProjectStatuss =GraduateProjectStatusService.getInstance().findAll();
        String graduateProjectStatuss_json = JSON.toJSONString(graduateProjectStatuss);
        //控制台打印结果
        System.out.println(graduateProjectStatuss_json);
        //浏览器展示结果
        response.getWriter().println(graduateProjectStatuss_json);
    }
}
