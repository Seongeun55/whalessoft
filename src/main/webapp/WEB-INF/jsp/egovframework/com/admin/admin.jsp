<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>웨일즈소프트 관리자모드</title>
    <!-- Custom fonts for this template-->
    <link href="/bootstrap/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="/css/admin.min.css" rel="stylesheet">
    <!-- Bootstrap core JavaScript-->
    <script src="/bootstrap/vendor/jquery/jquery.min.js"></script>
    <script src="/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="/bootstrap/vendor/jquery-easing/jquery.easing.min.js"></script>
</head>
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath }/admin/index.do">
                <div class="sidebar-brand-text mx-3">Admin Page</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath }/admin/index.do">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>관리자 홈</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Manage
            </div>
            
            <!-- Nav Item - banner&popup -->
           	<li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePage" aria-expanded="true" aria-controls="collapsePage">
                    <i class="fas fa-image"></i>
                    <span>배너 & 팝업관리</span>
                </a>
                <div id="collapsePage" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                         <a class="collapse-item" href="/admin/uss/ion/bnr/selectBannerList.do" target="_content">배너 관리</a>
                        <a class="collapse-item" href="/admin/uss/ion/pwm/listPopup.do" target="_content">팝업 관리</a>
                    </div>
                </div>
            </li>

			<li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseMenu" aria-expanded="true" aria-controls="collapseMenu">
                    <i class="fas fa-tasks"></i>
                    <span>메뉴관리</span>
                </a>
                <div id="collapseMenu" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">                    	                     
                        <a class="collapse-item" href="/admin/sym/mnu/mpm/AdminMenuListSelect.do" target="_content">메뉴 관리</a>
                        <a class="collapse-item" href="/admin/sym/prm/ProgramListManageSelect.do" target="_content">메뉴 RUL 관리</a>
                    </div>
                </div>
            </li>
            
            <!-- Nav Item - Utilities Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
                    <i class="fas fa-address-card"></i>
                    <span>회원관리</span>
                </a>
                <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                         <a class="collapse-item" href="/admin/uss/umt/AdminMberManage.do" target="_content">일반회원 관리</a>
                        <a class="collapse-item" href="/admin/uss/umt/AdminUserManage.do" target="_content">관리자 관리</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item - Pages Collapse Menu -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
                    <i class="fas fa-file"></i>
                    <span>게시판관리</span>
                </a>
                <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="/admin/cop/bbs/selectBBSMasterInfs.do" target="_content">게시판 관리</a>
                        <a class="collapse-item" href="/admin/cop/tpl/selectTemplateInfs.do" target="_content">템플릿 관리</a>
                    </div>
                </div>
            </li>
            
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsequestion" aria-expanded="true" aria-controls="collapsequestion">
                    <i class="fas fa-question"></i>
                    <span>질문관리</span>
                </a>
                <div id="collapsequestion" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                         <a class="collapse-item" href="/admin/uss/olh/faq/selectFaqList.do" target="_content">FAQ 관리</a>
                        <a class="collapse-item" href="/admin/uss/olh/qna/selectQnaAnswerList.do" target="_content">Q&A 관리</a>
                    </div>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-lock"></i>
                    <span>권한관리</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                         <a class="collapse-item" href="/admin/sec/ram/AdminAuthorList.do" target="_content">권한관리</a>
                         <a class="collapse-item" href="/admin/sec/rgm/AdminAuthorGroupList.do" target="_content">사용자권한 관리</a>
                        <a class="collapse-item" href="/admin/sec/rmt/AdminRoleList.do" target="_content">롤 관리</a>
                        <a class="collapse-item" href="/admin/uat/uap/selectLoginPolicyList.do" target="_content">IP 관리</a>
                    </div>
                </div>
            </li>

             <li class="nav-item">
                <a class="nav-link" href="/admin/sym/log/clg/SelectLoginLogList.do" target="_content">
                   <i class="fas fa-clipboard-list"></i>
                    <span>접속로그</span></a>
            </li>     
            
            <!-- Nav Item - Dashboard -->
             <li class="nav-item">
                <a class="nav-link" href="/admin/uss/sam/stp/StplatListInqire.do" target="_content">
                   <i class="fas fa-balance-scale"></i>
                    <span>약관 관리</span></a>
            </li>            

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <li class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-search fa-fw"></i>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                                aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small"
                                            placeholder="Search for..." aria-label="Search"
                                            aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button">
                                                <i class="fas fa-search fa-sm"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>
                        
                        <li class="nav-item">
               				<a class="nav-link" href="/main.do" target="_blank">
                  			<i class="fas fa-house-user"></i>
                    			<span>홈페이지 바로가기</span></a>
           				</li>   

						<div class="topbar-divider d-none d-sm-block" ></div>
							
                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${loginVO.name }</span>
                                <img class="img-profile rounded-circle"
                                    src="/bootstrap/img/undraw_profile.svg">
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="/uss/umt/AdminUserSelectUpdtView.do?selectedId=${loginVO.uniqId}" target="_content">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    	프로필
                                </a>
                                <a class="dropdown-item" href="/admin/sec/rgm/AdminAuthorGroupList.do" target="_content">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                   		권한관리
                                </a>
                                <a class="dropdown-item" href="/admin/sym/log/clg/SelectLoginLogList.do" target="_content">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    	접속로그
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                   		로그아웃
                                </a>
                            </div>
                        </li>
                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">
                	<iframe name="_content" src="${pageContext.request.contextPath}/AdminContent.do" onload="this.height=(this.contentWindow.document.body.scrollHeight+30);" width="100%" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="yes" vspace="0"></iframe>
                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Your Website 2020</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">로그아웃 하시겠습니까?</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="/uat/uia/securityLogout.do">Logout</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Custom scripts for all pages-->
    <script src="/js/admin.min.js"></script>    
</body>
</html>