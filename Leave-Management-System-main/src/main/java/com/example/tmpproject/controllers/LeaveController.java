package com.example.tmpproject.controllers;

import com.example.tmpproject.Models.LeaveModel;
import com.example.tmpproject.Models.TempLeave;
import com.example.tmpproject.oldentity.Department;
import com.example.tmpproject.oldentity.Employee;
import com.example.tmpproject.oldentity.LeaveApply;
import com.example.tmpproject.oldentity.LeaveType;
import com.example.tmpproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveController
{
    @Autowired
    private LeaveService leaveService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveTypeService leaveTypeService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DesignationService designationService;

    //employee can apply a leave
    @PostMapping("/addleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public LeaveApply addLeave(@RequestBody LeaveModel leavemodel)
    {
        LeaveApply leaveApply=new LeaveApply();
        leaveApply.setApplydate(CurrentDate.findCurrentDate());
        leaveApply.setFromDate(leavemodel.getFromDate());
        leaveApply.setToDate(leavemodel.getToDate());
        leaveApply.setStatus(0);
        leaveApply.setEmployee(employeeService.findEmployee(leavemodel.getEmployeeId()));
        leaveApply.setLeaveType(leaveTypeService.findLeaveType(leavemodel.getLeavetypeId()));
        leaveApply.setManager(null);
        leaveApply.setRemark(null);
        return leaveService.saveLeave(leaveApply);
    }
    @PostMapping("/findallleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<LeaveApply> findAllLeave()
    {
        return leaveService.findAllLeave();
    }

//    @PostMapping("/findleave")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public Leavemodule findLeaveByID(@RequestBody Leavemodule leavemodule)
//    {
//        Leavemodule leavemodule1=new Leavemodule();
//        LeaveApply leaveApply=leaveService.findLeave(leavemodule.getLeaveapplyId());
//        leavemodule1.setLeaveapplyId(leaveApply.getLeaveapplyId());
//        leavemodule1.setApplydate(leaveApply.getApplydate());
//        leavemodule1.setFromDate(leaveApply.getFromDate());
//        leavemodule1.setToDate(leaveApply.getToDate());
//        leavemodule1.setStatus(leaveApply.getStatus());
//        Employee employee=leaveApply.getEmployee();
//        leavemodule1.setEmployeeId(employee.getEmployeeId());
//        LeaveType leaveType=leaveApply.getLeaveType();
//        leavemodule1.setLeavetypeId(leaveType.getLeavetypeId());
//        return leavemodule1;
//    }
    //Hr or manager can Approved or not Approved
    @PostMapping("/applyleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public LeaveApply updateLeaveStatusById(@RequestBody LeaveModel leavemodel)
    {

        LeaveApply leaveApply=null;
        leaveApply=leaveService.findLeave(leavemodel.getLeaveapplyId());
        leaveApply.setStatus(leavemodel.getStatus());
        leaveApply.setManager(employeeService.findEmployee(leavemodel.getManagerId()));
        leaveApply.setRemark(leavemodel.getRemark());
        leaveApply.setRemarkdate(CurrentDate.findCurrentDate());

        return leaveService.saveLeave(leaveApply);
    }
    @PostMapping("/leaveofemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TempLeave> findLeaveByEmployee(@RequestBody LeaveModel leavemodel)
    {
        List<LeaveApply> leaveApplies=new ArrayList<>();
        List<TempLeave> tempLeaveList=new ArrayList<>();
        Employee employee=employeeService.findEmployee(leavemodel.getEmployeeId());
        LeaveType leaveType=new LeaveType();
        leaveApplies= leaveService.findByEmployee(employee);
        for(LeaveApply e:leaveApplies)
        {
            TempLeave tempLeave=new TempLeave();
            tempLeave.setLeaveapplyId(e.getLeaveapplyId());
            tempLeave.setFromDate(e.getFromDate());
            tempLeave.setToDate(e.getToDate());
            tempLeave.setApplydate(e.getApplydate());
            tempLeave.setStatus(e.getStatus());
            employee=e.getEmployee();
            String name=employee.getFirstName()+" "+employee.getLastName();
            tempLeave.setEmployeeName(name);
            leaveType=e.getLeaveType();
            tempLeave.setLeaveName(leaveType.getLeaveName());
            if(e.getStatus()!=0)
            {
                employee=e.getManager();
                String mname=employee.getFirstName()+" "+employee.getLastName();
                tempLeave.setManagerName(mname);
                tempLeave.setRemarkdate(e.getRemarkdate());
                tempLeave.setRemark(e.getRemark());
            }
            tempLeaveList.add(tempLeave);
        }
        return tempLeaveList;
    }
    @PostMapping("/findemployeeleavebydepartment")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TempLeave>findEmployeeLeaveByDepartment(@RequestBody Employee employee)
    {
        Employee employee1=new Employee();
        List<LeaveApply> leaveApplies=new ArrayList<>();
        List<TempLeave> tempLeaveList=new ArrayList<>();
        LeaveType leaveType=new LeaveType();
        employee1=employeeService.findEmployee(employee.getEmployeeId());
        Department department=employee1.getDepartment();
        leaveApplies =leaveService.findEmployeeLeaveByDepartment(department.getDepartmentId());
        for(LeaveApply e:leaveApplies)
        {
            TempLeave tempLeave=new TempLeave();
            tempLeave.setLeaveapplyId(e.getLeaveapplyId());
            tempLeave.setFromDate(e.getFromDate());
            tempLeave.setToDate(e.getToDate());
            tempLeave.setApplydate(e.getApplydate());
            tempLeave.setStatus(e.getStatus());
            employee=e.getEmployee();
            String name=employee.getFirstName()+" "+employee.getLastName();
            tempLeave.setEmployeeName(name);
            leaveType=e.getLeaveType();
            tempLeave.setLeaveName(leaveType.getLeaveName());
            if(e.getStatus()!=0)
            {
                employee1=e.getManager();
                String mname=employee1.getFirstName()+" "+employee1.getLastName();
                tempLeave.setManagerName(mname);
                tempLeave.setRemarkdate(e.getRemarkdate());
                tempLeave.setRemark(e.getRemark());
            }
            tempLeaveList.add(tempLeave);
        }
        return tempLeaveList;
    }
    @PostMapping("/findleavebydepartment")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<TempLeave> findLeaveByDepartment(@RequestBody LeaveModel leavemodel){
        List<LeaveApply> leaveApplies=new ArrayList<>();
        List<TempLeave> tempLeaveList=new ArrayList<>();
        LeaveType leaveType=new LeaveType();
        Employee employee1=new Employee();
        employee1=employeeService.findEmployee(leavemodel.getEmployeeId());
        Department department=employee1.getDepartment();
        leaveApplies=leaveService.findLeaveByDepartment(leavemodel.getStatus(),department.getDepartmentId());
        for(LeaveApply e:leaveApplies)
        {
            TempLeave tempLeave=new TempLeave();
            tempLeave.setLeaveapplyId(e.getLeaveapplyId());
            tempLeave.setFromDate(e.getFromDate());
            tempLeave.setToDate(e.getToDate());
            tempLeave.setApplydate(e.getApplydate());
            tempLeave.setStatus(e.getStatus());
            employee1=e.getEmployee();
            String name=employee1.getFirstName()+" "+employee1.getLastName();
            tempLeave.setEmployeeName(name);
            leaveType=e.getLeaveType();
            tempLeave.setLeaveName(leaveType.getLeaveName());
            if(e.getStatus()!=0)
            {
                employee1=e.getManager();
                String mname=employee1.getFirstName()+" "+employee1.getLastName();
                tempLeave.setManagerName(mname);
                tempLeave.setRemarkdate(e.getRemarkdate());
                tempLeave.setRemark(e.getRemark());
            }
            tempLeaveList.add(tempLeave);
        }
        return tempLeaveList;
    }

    @PostMapping("/findleavebyid")
    @CrossOrigin(origins = "http://localhost:4200")
    public TempLeave findByID(@RequestBody LeaveModel leavemodel){

        TempLeave tempLeave=new TempLeave();
        LeaveType leaveType=new LeaveType();
        LeaveApply leaveApply=leaveService.findLeave(leavemodel.getLeaveapplyId());
        tempLeave.setLeaveapplyId(leaveApply.getLeaveapplyId());
        tempLeave.setApplydate(leaveApply.getApplydate());
        tempLeave.setFromDate(leaveApply.getFromDate());
        tempLeave.setToDate(leaveApply.getToDate());
        tempLeave.setStatus(leaveApply.getStatus());
        Employee employee=leaveApply.getEmployee();
        String name=employee.getFirstName()+" "+employee.getLastName();
        tempLeave.setEmployeeName(name);
        leaveType=leaveApply.getLeaveType();
        tempLeave.setLeaveName(leaveType.getLeaveName());
        if(leaveApply.getStatus()!=0)
        {
            employee=leaveApply.getManager();
            String mname=employee.getFirstName()+" "+employee.getLastName();
            tempLeave.setManagerName(mname);
            tempLeave.setRemarkdate(leaveApply.getRemarkdate());
            tempLeave.setRemark(leaveApply.getRemark());
       }
        return tempLeave;
    }

    @PostMapping("/countpendingleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public long countBystatus(@RequestBody LeaveModel leavemodel)
    {
        Employee employee1=new Employee();
        employee1=employeeService.findEmployee(leavemodel.getEmployeeId());
        Department department=employee1.getDepartment();
        return leaveService.countBystatus(leavemodel.getStatus(),department.getDepartmentId());
    }

    @PostMapping("/countleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,Long> countByEmployee(@RequestBody LeaveModel leavemodel)
    {
        Map<String,Long> countofleave=new HashMap<String,Long>();
        long totalleave=leaveService.countByEmployee(leavemodel.getEmployeeId());
        long pending=leaveService.countByEmployeeAndstatus(0, leavemodel.getEmployeeId());
        long approved=leaveService.countByEmployeeAndstatus(1, leavemodel.getEmployeeId());
        long notapproved=leaveService.countByEmployeeAndstatus(2, leavemodel.getEmployeeId());
        countofleave.put("totalleave",totalleave);
        countofleave.put("pending",pending);
        countofleave.put("approved",approved);
        countofleave.put("notapproved",notapproved);
        return countofleave;
    }

    @PostMapping("/countmanagerleave")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,Long> countByManager(@RequestBody LeaveModel leavemodel)
    {
        Map<String,Long> countofleave=new HashMap<String,Long>();
        Employee employee1=new Employee();
        employee1=employeeService.findEmployee(leavemodel.getManagerId());
        Department department=employee1.getDepartment();
        long totalleave=leaveService.countLeaveByDepartment(department.getDepartmentId());
        long totalemployee=employeeService.countByDepartment(department.getDepartmentId());
        long pending=leaveService.countBystatus(leavemodel.getStatus(),department.getDepartmentId());
        long approved=leaveService.countByManagerAndstatus(1, leavemodel.getManagerId());
        long notapproved=leaveService.countByManagerAndstatus(2, leavemodel.getManagerId());
        countofleave.put("totalleave",totalleave);
        countofleave.put("totalemployee",totalemployee-1);
        countofleave.put("pending",pending);
        countofleave.put("approved",approved);
        countofleave.put("notapproved",notapproved);
        return countofleave;
    }

    @PostMapping("/countadmin")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String,Long> countByadmin()
    {
        Map<String,Long> counts=new HashMap<String,Long>();
        long department=departmentService.count();
        long manager=employeeService.countByUserRole(3);
        long leavetype=leaveTypeService.count();
        long employee=employeeService.count();
        long designation=designationService.count();
        counts.put("department",department);
        counts.put("manager",manager);
        counts.put("leavetype",leavetype);
        counts.put("employee",employee);
        counts.put("designation",designation);
        return counts;
    }
}
