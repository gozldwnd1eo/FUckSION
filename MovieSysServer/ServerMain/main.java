package ServerMain;

import java.util.ArrayList;

import Cinema.*;
import Film.*;
import Member.*;

public class main {
	public static void main(String[] args) {

		// ReviewDTO dto=new ReviewDTO();
		// dto.setCus_id("hg");
		// dto.setFilm_id("4");
		// dto.setrev_date("202011101700");
		// dto.setrev_content("123");
		// dto.setrev_starPoint(1);
		// FilmDAO dao=new FilmDAO();
		// FilmDTO dto =new FilmDTO();
		// dto.setFilm_name("gd");
		// boolean result =dao.insertFilm(dto);

		CustomerDTO dto = new CustomerDTO();
		MemberDAO dao = new MemberDAO();
		dto.setCus_id("js");
		dto.setCus_password("1234");
		dto.setCus_name("방진성");
		dto.setCus_phoneNum("010213");
		dto.setCus_account("1234");
		dto.setCus_gender("m");
		dto.setCus_money(1111);
		boolean result = dao.insertMember(dto);
		System.out.println(result);
		//
		// dto=new ReviewDTO();
		// dto.setCus_id("js");
		// dto.setFilm_id("4");
		// dto.setrev_date("202011101710");
		// dto.setrev_content("���ó����̴� ���ְ� ���������� ����");
		// dto.setrev_starPoint(10);
		// result =dao.insertReview(dto);
		// System.out.println(result);

		// ArrayList<ReviewDTO> arr=dao.displayReview("4");

		// System.out.println(arr.size());

		// ArrayList<ScreenDTO> arr =null;
		//
		// CinemaDAO dao=new CinemaDAO();
		//
		// arr=dao.screenDisplay("3", "1");
		//
		// System.out.println(arr.toString());
		// System.out.println(arr.size());

		// CinemaDAO dao=new CinemaDAO();
		//
		// TheaterDTO tdto=new TheaterDTO();
		// AuditoriumDTO adto=new AuditoriumDTO();
		//
		// tdto.settheater_address("�뱸�õ���xxx");
		// tdto.setad_id("admin");
		// tdto.settheater_area("�뱸");
		// tdto.settheater_name("�뱸���뱸���ó׸�");
		//
		// adto.setAuditName("1��");
		// adto.setaudi_num(1);
		// adto.settheater_id("3");
		// adto.setaudi_seatCnt(100);
		//
		// System.out.println(dao.insertTheater(tdto));
		// System.out.println(dao.insertAuditorium(adto));
		//
		// dao.insertTheater(dto)
		// dao.insertAuditorium(dto)
		// ArrayList<TheaterDTO>arr=dao.TheaterDisplay();
		// ArrayList<AuditoriumDTO>arr=dao.AuditoriumDisplay("2");
	}
}