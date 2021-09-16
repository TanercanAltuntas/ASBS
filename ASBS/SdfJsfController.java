package tr.asbs.controller.sdf;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.primefaces.json.JSONObject;

import lombok.Data;
import tr.asbs.AsbsJsfController;
import tr.asbs.common.base.BaseCrudController;
import tr.asbs.dto.kisi.KimlikDto;
import tr.asbs.dto.kurumsal.IlDto;
import tr.asbs.dto.sdf.SdfDto;
import tr.asbs.dto.yalin.kurumsal.IlYalinDto;
import tr.asbs.dto.yalin.kurumsal.IlceYalinDto;
import tr.asbs.dto.yalin.kurumsal.UlkeYalinDto;
import tr.asbs.service.kisi.KimlikService;
import tr.asbs.service.sdf.SdfService;

@AsbsJsfController
@Data
public class SdfJsfController extends BaseCrudController<SdfDto> {

	private SdfService sdfService;
	private KimlikService kimlikService;
	private SdfDto anlikSdf;
	private KimlikDto kimlikDto;
	private KimlikDto yeniKimlikDto;
	private KimlikDto ilkDegerDto;

	private Date kimlikDogumTarihi;
	private Date yeniKimlikDogumTarihi;
	private String kimlikDogumTarihiStr;
	private String yeniKimlikDogumTarihiStr;

	public SdfJsfController(SdfService sdfService, KimlikService kimlikService) {
		super(sdfService);
		this.sdfService = sdfService;
		this.kimlikService = kimlikService;
	}

	@Override
	protected SdfDto createNewModel() {
		return new SdfDto();
	}

	@Override
	public void init() {
		super.init();
		anlikSdf = sdfService.getir(20041629L);
		kimlikDto = kimlikService.getir(anlikSdf.getKisi_id().getVarsayilan_kimlik_id().getId());
		kimlikDogumTarihi = kimlikDogumTarihiAl(kimlikDto.getDogum_gunu(), kimlikDto.getDogum_ayi(), kimlikDto.getDogum_yili());
		kimlikDogumTarihiStr = kimlikDogumTarihiStringAl(kimlikDogumTarihi);
		JSONObject jsonKimlik = new JSONObject(anlikSdf.getYeni_kisi());
		yeniKimlikDto = kimlikService.sdfYeniKimlikSetleDto(jsonKimlik, kimlikDto);
		yeniKimlikDogumTarihi = kimlikDogumTarihiAl(yeniKimlikDto.getDogum_gunu(), yeniKimlikDto.getDogum_ayi(), yeniKimlikDto.getDogum_yili());
		yeniKimlikDogumTarihiStr = kimlikDogumTarihiStringAl(yeniKimlikDogumTarihi);
	}

	public void eskiVeriYeniVeriDegistir(String kolon_adi) {

		if (kolon_adi.equals("baba_adi")) {
			String tempBabaAdi = kimlikDto.getBaba_adi();
			kimlikDto.setBaba_adi(yeniKimlikDto.getBaba_adi());
			yeniKimlikDto.setBaba_adi(tempBabaAdi);
		}

		else if (kolon_adi.equals("ana_adi")) {
			String tempAnaAdi = kimlikDto.getAna_adi();
			kimlikDto.setAna_adi(yeniKimlikDto.getAna_adi());
			yeniKimlikDto.setAna_adi(tempAnaAdi);
		}
		
		else if (kolon_adi.equals("soyad")) {
			String tempSoyad = kimlikDto.getSoyad();
			kimlikDto.setSoyad(yeniKimlikDto.getSoyad());
			yeniKimlikDto.setSoyad(tempSoyad);
		}
		
		else if (kolon_adi.equals("ad")) {
			String tempAd = kimlikDto.getAd();
			kimlikDto.setAd(yeniKimlikDto.getAd());
			yeniKimlikDto.setAd(tempAd);
		}
		
		else if (kolon_adi.equals("mahalle")) {
			String tempMahalle = kimlikDto.getMahalle();
			kimlikDto.setMahalle(yeniKimlikDto.getMahalle());
			yeniKimlikDto.setMahalle(tempMahalle);
			
		}
		
		else if (kolon_adi.equals("dogum_yeri")) {
			String tempDogumYeri = kimlikDto.getDogum_yeri();
			kimlikDto.setDogum_yeri(yeniKimlikDto.getDogum_yeri());
			yeniKimlikDto.setDogum_yeri(tempDogumYeri);
		}

		else if (kolon_adi.equals("dogum_tarihi")) {
			Date tempDogumTarihi = kimlikDogumTarihi;
			kimlikDogumTarihi = yeniKimlikDogumTarihi;
			yeniKimlikDogumTarihi = tempDogumTarihi;
			
			Calendar kimlikCal = Calendar.getInstance();
			kimlikCal.setTime(kimlikDogumTarihi);

			kimlikDto.setDogum_gunu(kimlikCal.get(Calendar.DAY_OF_MONTH)+"");
			kimlikDto.setDogum_ayi((kimlikCal.get(Calendar.MONTH)+1)+"");
			kimlikDto.setDogum_yili(kimlikCal.get(Calendar.YEAR)+"");

			kimlikCal.setTime(yeniKimlikDogumTarihi);

			yeniKimlikDto.setDogum_gunu(kimlikCal.get(Calendar.DAY_OF_MONTH)+"");
			yeniKimlikDto.setDogum_ayi((kimlikCal.get(Calendar.MONTH)+1)+"");
			yeniKimlikDto.setDogum_yili(kimlikCal.get(Calendar.YEAR)+"");

			kimlikDogumTarihiStr = kimlikDogumTarihiStringAl(kimlikDogumTarihi);
			yeniKimlikDogumTarihiStr = kimlikDogumTarihiStringAl(yeniKimlikDogumTarihi);
		}

		else if (kolon_adi.equals("ulke_id")) {
			UlkeYalinDto tempDogumYeri = kimlikDto.getUlke_id();
			kimlikDto.setUlke_id(yeniKimlikDto.getUlke_id());
			yeniKimlikDto.setUlke_id(tempDogumYeri);
		}
		
		else if(kolon_adi.equals("kimlik_no")) {
			String tempTckn = kimlikDto.getKimlik_no();
			kimlikDto.setKimlik_no(yeniKimlikDto.getKimlik_no());
			yeniKimlikDto.setKimlik_no(tempTckn);
		}
		
		
		else if(kolon_adi.equals("ilce_id")) {
			IlYalinDto tempIlce = kimlikDto.getNufusa_kayitli_ilce_id().getIl_id();
			kimlikDto.getNufusa_kayitli_ilce_id().setIl_id(yeniKimlikDto.getNufusa_kayitli_ilce_id().getIl_id());
			yeniKimlikDto.getNufusa_kayitli_ilce_id().setIl_id(tempIlce);
			
		}
		
		else if(kolon_adi.equals("il_id")) {
			IlYalinDto tempIl = kimlikDto.getNufusa_kayitli_ilce_id().getIl_id();
			kimlikDto.setNufusa_kayitli_ilce_id(yeniKimlikDto.getNufusa_kayitli_ilce_id());
			yeniKimlikDto.setNufusa_kayitli_ilce_id(new IlceYalinDto());
			yeniKimlikDto.getNufusa_kayitli_ilce_id().setAd("-");
			yeniKimlikDto.getNufusa_kayitli_ilce_id().setIl_id(tempIl);
		}
		
		else if(kolon_adi.equals("ilce_id2")) {
			IlceYalinDto tempIlce2 = kimlikDto.getNufusa_kayitli_ilce_id();
			kimlikDto.setNufusa_kayitli_ilce_id(yeniKimlikDto.getNufusa_kayitli_ilce_id());
			yeniKimlikDto.setNufusa_kayitli_ilce_id(tempIlce2);
		}
		
		else if(kolon_adi.equals("cinsiyet")) {
			int tempCinsiyet = kimlikDto.getCinsiyet();
			kimlikDto.setCinsiyet(yeniKimlikDto.getCinsiyet());
			yeniKimlikDto.setCinsiyet(tempCinsiyet);
		}
		
	}
	
	public Date kimlikDogumTarihiAl(String gun, String ay, String yil){
		Calendar cal = Calendar.getInstance();
		Date dogumTarihi = null;
		try {
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(gun));
			cal.set(Calendar.MONTH, Integer.parseInt(ay)-1);
			cal.set(Calendar.YEAR, Integer.parseInt(yil));
			
			dogumTarihi = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dogumTarihi;
	}

	public String kimlikDogumTarihiStringAl(Date dogumTarihi){
		
		return dogumTarihi != null ? new SimpleDateFormat("dd/MM/yyyy").format(dogumTarihi) : "";
	}

	public void onItemSecilenListener() {
		if(kimlikDto != null) {		
			kimlikDto.getNufusa_kayitli_ilce_id().setAd("");
			kimlikDto.getNufusa_kayitli_ilce_id().setId((long) 0); 
		}
		
	}
}
