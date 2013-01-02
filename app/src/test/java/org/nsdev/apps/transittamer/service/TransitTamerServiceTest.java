package org.nsdev.apps.transittamer.service;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nsdev.apps.transittamer.TransitTamerServiceProvider;
import retrofit.http.Callback;
import retrofit.http.RetrofitError;

import java.util.concurrent.CountDownLatch;

/**
 * Created by neal 12-12-15 4:40 PM
 */
@RunWith(RobolectricTestRunner.class)
public class TransitTamerServiceTest {

    TransitTamerServiceAsync transitTamerService;
    StopDistancesResponse stopDistancesResponse;

    @Before
    public void setup() {
        TransitTamerServiceProvider provider = new TransitTamerServiceProvider();
        transitTamerService = provider.getService();
    }

    @Test
    public void testStops() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1);

        Robolectric.addPendingHttpResponse(200, "{\n" +
                "ns: \"TransitTamer.Stops\",\n" +
                "near: \"0100110010111000101100010110110001101111001010110101\",\n" +
                "results: [\n" +
                "{\n" +
                "dis: 0.000022593393966120664,\n" +
                "obj: {\n" +
                "stop_id: \"7161\",\n" +
                "stop_code: \"7161\",\n" +
                "stop_name: \"EB SCURFIELD DR@SCANLON GR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.222014,\n" +
                "lat: 51.124482\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f04\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00002778993054701221,\n" +
                "obj: {\n" +
                "stop_id: \"7158\",\n" +
                "stop_code: \"7158\",\n" +
                "stop_name: \"WB SCURFIELD DR@SCANLON GR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.223435,\n" +
                "lat: 51.124624\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f01\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.000029159204317736585,\n" +
                "obj: {\n" +
                "stop_id: \"7160\",\n" +
                "stop_code: \"7160\",\n" +
                "stop_name: \"NB SCURFIELD DR@SCANLON GA NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.224746,\n" +
                "lat: 51.122541\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f03\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00003296187716490092,\n" +
                "obj: {\n" +
                "stop_id: \"7170\",\n" +
                "stop_code: \"7170\",\n" +
                "stop_name: \"SB SCURFIELD DR@SCHOONER CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.224291,\n" +
                "lat: 51.121784\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f0d\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.000034360859623345166,\n" +
                "obj: {\n" +
                "stop_id: \"7169\",\n" +
                "stop_code: \"7169\",\n" +
                "stop_name: \"SB SCURFIELD DR@SCHUBERT GA NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.225127,\n" +
                "lat: 51.124053\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f0c\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00003756130237152435,\n" +
                "obj: {\n" +
                "stop_id: \"7156\",\n" +
                "stop_code: \"7156\",\n" +
                "stop_name: \"WB SCURFIELD DR@SCANLON PL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.219192,\n" +
                "lat: 51.124109\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001eff\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.000041554674017664295,\n" +
                "obj: {\n" +
                "stop_id: \"7162\",\n" +
                "stop_code: \"7162\",\n" +
                "stop_name: \"SB SCURFIELD DR@SCANLON PL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.21853,\n" +
                "lat: 51.123464\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f05\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00005361783603312307,\n" +
                "obj: {\n" +
                "stop_id: \"7159\",\n" +
                "stop_code: \"7159\",\n" +
                "stop_name: \"NB SCURFIELD DR@SCRIPPS LD NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.222632,\n" +
                "lat: 51.120135\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f02\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00006979966674202277,\n" +
                "obj: {\n" +
                "stop_id: \"7157\",\n" +
                "stop_code: \"7157\",\n" +
                "stop_name: \"WB SCURFIELD DR@SCOTIA PT NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.216013,\n" +
                "lat: 51.12255\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f00\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00008442098452306547,\n" +
                "obj: {\n" +
                "stop_id: \"7163\",\n" +
                "stop_code: \"7163\",\n" +
                "stop_name: \"EB SCURFIELD DR@SCOTIA PT NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.214744,\n" +
                "lat: 51.122249\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f06\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00008988627219897563,\n" +
                "obj: {\n" +
                "stop_id: \"7171\",\n" +
                "stop_code: \"7171\",\n" +
                "stop_name: \"SB SCURFIELD DR@SCEPTRE CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.223156,\n" +
                "lat: 51.118078\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f0e\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00010209130601726523,\n" +
                "obj: {\n" +
                "stop_id: \"7154\",\n" +
                "stop_code: \"7154\",\n" +
                "stop_name: \"WB SCENIC ACRS BV@SCURFIELD WY NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.215336,\n" +
                "lat: 51.119313\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001efd\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00010549551722823041,\n" +
                "obj: {\n" +
                "stop_id: \"8573\",\n" +
                "stop_code: \"8573\",\n" +
                "stop_name: \"WB SCURFIELD DR@SCOTIA LD NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.212747,\n" +
                "lat: 51.122436\n" +
                "},\n" +
                "_id: \"50c972140f8190491f0034e2\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00010589768508123976,\n" +
                "obj: {\n" +
                "stop_id: \"8574\",\n" +
                "stop_code: \"8574\",\n" +
                "stop_name: \"EB SCURFIELD DR@SCENIC GD NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.21276,\n" +
                "lat: 51.12222\n" +
                "},\n" +
                "_id: \"50c972140f8190491f0034e3\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.0001081338028133255,\n" +
                "obj: {\n" +
                "stop_id: \"7145\",\n" +
                "stop_code: \"7145\",\n" +
                "stop_name: \"NB SCENIC ACRS BV@SCENIC PK GA NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.217762,\n" +
                "lat: 51.117698\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001ef5\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00010954207253535469,\n" +
                "obj: {\n" +
                "stop_id: \"7155\",\n" +
                "stop_code: \"7155\",\n" +
                "stop_name: \"SB SCENIC ACRS BV@SCENIC HL CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.218988,\n" +
                "lat: 51.117278\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001efe\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00011217981718625569,\n" +
                "obj: {\n" +
                "stop_id: \"7144\",\n" +
                "stop_code: \"7144\",\n" +
                "stop_name: \"EB SCENIC ACRS BV@SCURFIELD WY NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.214135,\n" +
                "lat: 51.119321\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001ef4\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00012693499047493003,\n" +
                "obj: {\n" +
                "stop_id: \"5222\",\n" +
                "stop_code: \"5222\",\n" +
                "stop_name: \"EB SCENIC ACRS BV@SCENIC ACRS DR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.221512,\n" +
                "lat: 51.115944\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0005d0\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00012950455779652464,\n" +
                "obj: {\n" +
                "stop_id: \"7164\",\n" +
                "stop_code: \"7164\",\n" +
                "stop_name: \"SB SCURFIELD DR@SCENIC GD NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.211196,\n" +
                "lat: 51.120654\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f07\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00013000649754994748,\n" +
                "obj: {\n" +
                "stop_id: \"7153\",\n" +
                "stop_code: \"7153\",\n" +
                "stop_name: \"WB SCENIC ACRS BV@SCURFIELD DR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.211887,\n" +
                "lat: 51.119627\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001efc\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00013015369758187707,\n" +
                "obj: {\n" +
                "stop_id: \"7631\",\n" +
                "stop_code: \"7631\",\n" +
                "stop_name: \"EB ARBR LK RD@W OF CROWFOOT RI NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.213486,\n" +
                "lat: 51.128201\n" +
                "},\n" +
                "_id: \"50c972140f8190491f002934\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.0001321431808207121,\n" +
                "obj: {\n" +
                "stop_id: \"5939\",\n" +
                "stop_code: \"5939\",\n" +
                "stop_name: \"WB ARBR LK RD@WATERGV M.H.PK\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.214234,\n" +
                "lat: 51.12883\n" +
                "},\n" +
                "_id: \"50c972130f8190491f00086e\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00013317029294140483,\n" +
                "obj: {\n" +
                "stop_id: \"7165\",\n" +
                "stop_code: \"7165\",\n" +
                "stop_name: \"SB SCENIC ACRS DR@SCENIC ACRS BV NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.222506,\n" +
                "lat: 51.115571\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f08\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00013903773905699223,\n" +
                "obj: {\n" +
                "stop_id: \"6535\",\n" +
                "stop_code: \"6535\",\n" +
                "stop_name: \"NB SCURFIELD DR@SCENIC ACRS BV NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.210694,\n" +
                "lat: 51.119976\n" +
                "},\n" +
                "_id: \"50c972130f8190491f001440\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00014705953655121248,\n" +
                "obj: {\n" +
                "stop_id: \"8584\",\n" +
                "stop_code: \"8584\",\n" +
                "stop_name: \"EB ARBR LK RD@CROWFOOT RI NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.210168,\n" +
                "lat: 51.126808\n" +
                "},\n" +
                "_id: \"50c972140f8190491f0034ed\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00014761521405538299,\n" +
                "obj: {\n" +
                "stop_id: \"8325\",\n" +
                "stop_code: \"8325\",\n" +
                "stop_name: \"SB ARBR LK RD@ARBR MDWS CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.218035,\n" +
                "lat: 51.131223\n" +
                "},\n" +
                "_id: \"50c972140f8190491f003408\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015243581004406056,\n" +
                "obj: {\n" +
                "stop_id: \"9197\",\n" +
                "stop_code: \"9197\",\n" +
                "stop_name: \"WB ARBR LK RD@ARBR GV CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.209651,\n" +
                "lat: 51.126841\n" +
                "},\n" +
                "_id: \"50c972150f8190491f003f80\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015458789331558364,\n" +
                "obj: {\n" +
                "stop_id: \"3857\",\n" +
                "stop_code: \"3857\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.208211,\n" +
                "lat: 51.123704\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0000e0\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015597805923344183,\n" +
                "obj: {\n" +
                "stop_id: \"3858\",\n" +
                "stop_code: \"3858\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.208174,\n" +
                "lat: 51.124323\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0000e1\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015690245889101478,\n" +
                "obj: {\n" +
                "stop_id: \"7152\",\n" +
                "stop_code: \"7152\",\n" +
                "stop_name: \"NB SCENIC ACRS DR@SCANDIA BA NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.22097,\n" +
                "lat: 51.114249\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001efb\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.0001571994412245664,\n" +
                "obj: {\n" +
                "stop_id: \"9876\",\n" +
                "stop_code: \"9876\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207962,\n" +
                "lat: 51.123572\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a65\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015895775917304945,\n" +
                "obj: {\n" +
                "stop_id: \"9878\",\n" +
                "stop_code: \"9878\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.20782,\n" +
                "lat: 51.123793\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a67\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015935884512325953,\n" +
                "obj: {\n" +
                "stop_id: \"5958\",\n" +
                "stop_code: \"5958\",\n" +
                "stop_name: \"NB ARBR LK RD@ARBR MDWS CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.218064,\n" +
                "lat: 51.131935\n" +
                "},\n" +
                "_id: \"50c972130f8190491f000880\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00015989857658688144,\n" +
                "obj: {\n" +
                "stop_id: \"9880\",\n" +
                "stop_code: \"9880\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.20771,\n" +
                "lat: 51.123476\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a69\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016127029123709635,\n" +
                "obj: {\n" +
                "stop_id: \"9879\",\n" +
                "stop_code: \"9879\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207598,\n" +
                "lat: 51.12368\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a68\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.000162452762266131,\n" +
                "obj: {\n" +
                "stop_id: \"9877\",\n" +
                "stop_code: \"9877\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207473,\n" +
                "lat: 51.12338\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a66\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016251509601005902,\n" +
                "obj: {\n" +
                "stop_id: \"6853\",\n" +
                "stop_code: \"6853\",\n" +
                "stop_name: \"CROWFOOT CL @ CROWFOOT WY NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207746,\n" +
                "lat: 51.125006\n" +
                "},\n" +
                "_id: \"50c972140f8190491f00156d\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016281262680772676,\n" +
                "obj: {\n" +
                "stop_id: \"6842\",\n" +
                "stop_code: \"6842\",\n" +
                "stop_name: \"CROWFOOT WY @ CROWFOOT CI NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207718,\n" +
                "lat: 51.125005\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001562\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.0001636335858116616,\n" +
                "obj: {\n" +
                "stop_id: \"9875\",\n" +
                "stop_code: \"9875\",\n" +
                "stop_name: \"Crowfoot LRT Station\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207375,\n" +
                "lat: 51.123585\n" +
                "},\n" +
                "_id: \"50c972150f8190491f004a64\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016694789068833794,\n" +
                "obj: {\n" +
                "stop_id: \"3817\",\n" +
                "stop_code: \"3817\",\n" +
                "stop_name: \"Crowfoot LRT Station eb\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207093,\n" +
                "lat: 51.122571\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0000bc\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016866172423970991,\n" +
                "obj: {\n" +
                "stop_id: \"7630\",\n" +
                "stop_code: \"7630\",\n" +
                "stop_name: \"WB CROWFOOT WY@CROWFOOT CR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.207533,\n" +
                "lat: 51.125936\n" +
                "},\n" +
                "_id: \"50c972140f8190491f002933\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016925155968866364,\n" +
                "obj: {\n" +
                "stop_id: \"3816\",\n" +
                "stop_code: \"3816\",\n" +
                "stop_name: \"Crowfoot LRT Station wb\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.206871,\n" +
                "lat: 51.122691\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0000bb\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00016936959335210245,\n" +
                "obj: {\n" +
                "stop_id: \"7166\",\n" +
                "stop_code: \"7166\",\n" +
                "stop_name: \"SB SCENIC ACRS DR@SCANDIA RI NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.220684,\n" +
                "lat: 51.113549\n" +
                "},\n" +
                "_id: \"50c972140f8190491f001f09\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00017671802188774273,\n" +
                "obj: {\n" +
                "stop_id: \"5179\",\n" +
                "stop_code: \"5179\",\n" +
                "stop_name: \"EB TUSC BV@TUSC SPRS BV NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.238071,\n" +
                "lat: 51.125331\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0005aa\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00017761034477115287,\n" +
                "obj: {\n" +
                "stop_id: \"6267\",\n" +
                "stop_code: \"6267\",\n" +
                "stop_name: \"EB CROWFOOT WY@CROWFOOT CR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.206405,\n" +
                "lat: 51.125208\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0009a8\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00018000530522567948,\n" +
                "obj: {\n" +
                "stop_id: \"5850\",\n" +
                "stop_code: \"5850\",\n" +
                "stop_name: \"NB CROWFOOT CR@CROWFOOT WY NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.206473,\n" +
                "lat: 51.125974\n" +
                "},\n" +
                "_id: \"50c972130f8190491f00081c\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00018023175078396887,\n" +
                "obj: {\n" +
                "stop_id: \"8300\",\n" +
                "stop_code: \"8300\",\n" +
                "stop_name: \"SB ARBR LK RD@ARBR STONE CR NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.217863,\n" +
                "lat: 51.133144\n" +
                "},\n" +
                "_id: \"50c972140f8190491f0033ef\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00018475659853167998,\n" +
                "obj: {\n" +
                "stop_id: \"8432\",\n" +
                "stop_code: \"8432\",\n" +
                "stop_name: \"NB TUSC SPRS BV@TUSC BV NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.238494,\n" +
                "lat: 51.126159\n" +
                "},\n" +
                "_id: \"50c972140f8190491f00345c\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00018647495449348065,\n" +
                "obj: {\n" +
                "stop_id: \"3835\",\n" +
                "stop_code: \"3835\",\n" +
                "stop_name: \"TUSCANY BV@TUSCANY SPRINGS BV NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.238884,\n" +
                "lat: 51.125611\n" +
                "},\n" +
                "_id: \"50c972130f8190491f0000ce\"\n" +
                "}\n" +
                "},\n" +
                "{\n" +
                "dis: 0.00018732715664149816,\n" +
                "obj: {\n" +
                "stop_id: \"8429\",\n" +
                "stop_code: \"8429\",\n" +
                "stop_name: \"NB TUSC SPRS BV@TUSCARORA CL NW\",\n" +
                "stop_desc: \"\",\n" +
                "zone_id: \"\",\n" +
                "stop_url: \"\",\n" +
                "location_type: \"0\",\n" +
                "loc: {\n" +
                "lon: -114.238206,\n" +
                "lat: 51.127142\n" +
                "},\n" +
                "_id: \"50c972140f8190491f003459\"\n" +
                "}\n" +
                "}\n" +
                "],\n" +
                "stats: {\n" +
                "time: 1,\n" +
                "btreelocs: 0,\n" +
                "nscanned: 748,\n" +
                "objectsLoaded: 146,\n" +
                "avgDistance: 0.0001279606003044939,\n" +
                "maxDistance: 0.0001876402370635678\n" +
                "},\n" +
                "ok: 1\n" +
                "}");

        stopDistancesResponse = null;

        transitTamerService.getNearestStops(-114.2223, 51.1232, 100, new Callback<StopDistancesResponse>() {
            @Override
            public void success(StopDistancesResponse stopDistancesResponse) {
                TransitTamerServiceTest.this.stopDistancesResponse = stopDistancesResponse;
                latch.countDown();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                latch.countDown();
            }
        });

        latch.await();

        Assert.assertNotNull(stopDistancesResponse);

        Assert.assertEquals(1, stopDistancesResponse.ok);
        Assert.assertEquals(50, stopDistancesResponse.results.size());

        for(StopDistance dist : stopDistancesResponse.results) {
            Assert.assertTrue(dist.dis > 0.0);
            Assert.assertNotNull(dist.obj);
            Assert.assertEquals(dist.obj.stopCode, dist.obj.stopId);
            Assert.assertNotNull(dist.obj.loc);
            Assert.assertTrue(dist.obj.loc.lat != 0.0);
            Assert.assertTrue(dist.obj.loc.lon != 0.0);
        }
    }

    private RoutesResponse routesResponse;

    @Test
    public void testFindRoute() throws InterruptedException {

        Robolectric.addPendingHttpResponse(200, "{\n" +
                "ok: 1,\n" +
                "routes: [\n" +
                "{\n" +
                "route_id: \"10-20220\",\n" +
                "route_short_name: \"10\",\n" +
                "route_long_name: \"Dalhousie/Southcentre\",\n" +
                "route_desc: \"\",\n" +
                "route_type: \"3\",\n" +
                "route_url: \"\",\n" +
                "_id: \"50c972130f8190491f0009c9\"\n" +
                "},\n" +
                "{\n" +
                "route_id: \"10-20226\",\n" +
                "route_short_name: \"10\",\n" +
                "route_long_name: \"Dalhousie/Southcentre\",\n" +
                "route_desc: \"\",\n" +
                "route_type: \"3\",\n" +
                "route_url: \"\",\n" +
                "_id: \"50c972130f8190491f0009ca\"\n" +
                "}\n" +
                "]\n" +
                "}");

        final CountDownLatch latch = new CountDownLatch(1);

        routesResponse = null;

        transitTamerService.getRoutesForShortName("10", new Callback<RoutesResponse>() {
            @Override
            public void success(RoutesResponse routesResponse) {
                TransitTamerServiceTest.this.routesResponse = routesResponse;
                latch.countDown();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                latch.countDown();
            }
        });

        latch.await();

        Assert.assertNotNull(routesResponse);

        Assert.assertEquals(1, routesResponse.ok);
        Assert.assertEquals(2, routesResponse.routes.size());

        for(Route route : routesResponse.routes) {
            Assert.assertEquals("10", route.routeShortName);
        }

    }

    @Test
    public void testAgency() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1);

        Robolectric.addPendingHttpResponse(200, "{\n" +
                "ok: 1,\n" +
                "agency: {\n" +
                "agency_name: \"Calgary Transit\",\n" +
                "agency_url: \"http://www.calgarytransit.com\",\n" +
                "agency_timezone: \"America/Edmonton\",\n" +
                "agency_lang: \"\",\n" +
                "agency_phone: \"403-262-1000\",\n" +
                "_id: \"50c972130f8190491f000001\"\n" +
                "}\n" +
                "}");

        transitTamerService.getAgency(new Callback<AgencyResponse>() {
            @Override
            public void success(AgencyResponse agencyResponse) {

                Assert.assertEquals(1, agencyResponse.ok);
                Assert.assertEquals("Calgary Transit", agencyResponse.agency.agencyName);
                Assert.assertEquals("America/Edmonton", agencyResponse.agency.agencyTimezone);

                latch.countDown();

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                latch.countDown();
            }
        });

        latch.await();
    }

    private CalendarsResponse calendarsResponse = null;

    @Test
    public void testCalendars() throws InterruptedException {

        Robolectric.addPendingHttpResponse(200, "{\n" +
                "ok: 1,\n" +
                "calendars: [\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-03\",\n" +
                "monday: \"1\",\n" +
                "tuesday: \"1\",\n" +
                "wednesday: \"1\",\n" +
                "thursday: \"1\",\n" +
                "friday: \"1\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121210\",\n" +
                "end_date: \"20121214\",\n" +
                "_id: \"50c972130f8190491f000002\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-03-1111000\",\n" +
                "monday: \"1\",\n" +
                "tuesday: \"1\",\n" +
                "wednesday: \"1\",\n" +
                "thursday: \"1\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121210\",\n" +
                "end_date: \"20121214\",\n" +
                "_id: \"50c972130f8190491f000003\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-03-0000100\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"1\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121210\",\n" +
                "end_date: \"20121214\",\n" +
                "_id: \"50c972130f8190491f000004\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSSA-Saturday-01\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"1\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121215\",\n" +
                "end_date: \"20121215\",\n" +
                "_id: \"50c972130f8190491f000005\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSSU-Sunday-01\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"1\",\n" +
                "start_date: \"20121216\",\n" +
                "end_date: \"20130218\",\n" +
                "_id: \"50c972130f8190491f000006\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-05\",\n" +
                "monday: \"1\",\n" +
                "tuesday: \"1\",\n" +
                "wednesday: \"1\",\n" +
                "thursday: \"1\",\n" +
                "friday: \"1\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121217\",\n" +
                "end_date: \"20130308\",\n" +
                "_id: \"50c972130f8190491f000007\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-05-0000100\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"1\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121217\",\n" +
                "end_date: \"20130308\",\n" +
                "_id: \"50c972130f8190491f000008\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSWK-Weekday-05-1111000\",\n" +
                "monday: \"1\",\n" +
                "tuesday: \"1\",\n" +
                "wednesday: \"1\",\n" +
                "thursday: \"1\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121217\",\n" +
                "end_date: \"20130308\",\n" +
                "_id: \"50c972130f8190491f000009\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSSA-Saturday-02\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"1\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121222\",\n" +
                "end_date: \"20130309\",\n" +
                "_id: \"50c972130f8190491f00000a\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1BUSSU-Sunday-02\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"1\",\n" +
                "start_date: \"20121223\",\n" +
                "end_date: \"20130310\",\n" +
                "_id: \"50c972130f8190491f00000b\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-pBxDec25-Sunday-25\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"1\",\n" +
                "start_date: \"20121225\",\n" +
                "end_date: \"20121225\",\n" +
                "_id: \"50c972130f8190491f00000c\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1LRTWK-Weekday-03\",\n" +
                "monday: \"1\",\n" +
                "tuesday: \"1\",\n" +
                "wednesday: \"1\",\n" +
                "thursday: \"1\",\n" +
                "friday: \"1\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121210\",\n" +
                "end_date: \"20130308\",\n" +
                "_id: \"50c972130f8190491f00000d\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1LRTSA-Saturday-02\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"1\",\n" +
                "sunday: \"0\",\n" +
                "start_date: \"20121215\",\n" +
                "end_date: \"20130309\",\n" +
                "_id: \"50c972130f8190491f00000e\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-1LRTSU-Sunday-02\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"1\",\n" +
                "start_date: \"20121216\",\n" +
                "end_date: \"20130310\",\n" +
                "_id: \"50c972130f8190491f00000f\"\n" +
                "},\n" +
                "{\n" +
                "service_id: \"2012DE-pTxDec25-Sunday-25\",\n" +
                "monday: \"0\",\n" +
                "tuesday: \"0\",\n" +
                "wednesday: \"0\",\n" +
                "thursday: \"0\",\n" +
                "friday: \"0\",\n" +
                "saturday: \"0\",\n" +
                "sunday: \"1\",\n" +
                "start_date: \"20121225\",\n" +
                "end_date: \"20121225\",\n" +
                "_id: \"50c972130f8190491f000010\"\n" +
                "}\n" +
                "]\n" +
                "}");

        final CountDownLatch latch = new CountDownLatch(1);

        calendarsResponse = null;

        transitTamerService.getAllCalendars(new Callback<CalendarsResponse>() {
            @Override
            public void success(CalendarsResponse calendarsResponse) {
                TransitTamerServiceTest.this.calendarsResponse = calendarsResponse;
                latch.countDown();

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                latch.countDown();
            }
        });

        latch.await();

        Assert.assertNotNull(calendarsResponse);

        for(Calendar calendar : calendarsResponse.calendars)
        {
            Assert.assertNotNull(calendar.startDate);
            Assert.assertNotNull(calendar.endDate);

            int comparison = calendar.startDate.compareTo(calendar.endDate);
            // System.err.println("Start: "+ calendar.startDate + " End: "+calendar.endDate + " comparison: "+comparison);
            Assert.assertTrue(comparison != 1);
        }

    }
}
