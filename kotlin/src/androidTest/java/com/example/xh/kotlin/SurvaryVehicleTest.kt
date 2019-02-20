package com.example.xh.kotlin

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader

@RunWith(AndroidJUnit4::class)
class SurvaryVehicleTest {
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    val chinaVehicles = listOf<String>("vehicle50001", "vehicle50003", "vehicle50004", "vehicle50005", "vehicle50006",
            "vehicle50007", "vehicle50009", "vehicle50010", "vehicle50011", "vehicle50012",
            "vehicle50013", "vehicle50014", "vehicle50015", "vehicle50017", "vehicle50018",
            "vehicle50019", "vehicle50020", "vehicle50021", "vehicle50022", "vehicle50023",
            "vehicle50024", "vehicle50027", "vehicle50028", "vehicle50029", "vehicle50030",
            "vehicle50031", "vehicle50033", "vehicle50034", "vehicle50035", "vehicle50036",
            "vehicle50037", "vehicle50040", "vehicle50041", "vehicle50042", "vehicle50043",
            "vehicle50044", "vehicle50045", "vehicle50049", "vehicle50052", "vehicle50053",
            "vehicle50055", "vehicle50058", "vehicle50059", "vehicle50060", "vehicle50061",
            "vehicle50062", "vehicle50063", "vehicle50064", "vehicle50065", "vehicle50066",
            "vehicle50067", "vehicle50068", "vehicle50069", "vehicle50072", "vehicle50073",
            "vehicle50074", "vehicle50075", "vehicle50076", "vehicle50077", "vehicle50078",
            "vehicle50080", "vehicle50082", "vehicle50083", "vehicle50084", "vehicle50085",
            "vehicle50086", "vehicle50087", "vehicle50088", "vehicle50089", "vehicle50090",
            "vehicle50091", "vehicle50092", "vehicle50093", "vehicle50094", "vehicle50095",
            "vehicle50096", "vehicle50097", "vehicle50098", "vehicle50099", "vehicle50100",
            "vehicle50103", "vehicle50106", "vehicle50107", "vehicle50108", "vehicle50109",
            "vehicle50110", "vehicle50111", "vehicle50112", "vehicle50113", "vehicle50115",
            "vehicle50117", "vehicle50119", "vehicle50120", "vehicle50121", "vehicle50122",
            "vehicle50123", "vehicle50124", "vehicle50125", "vehicle50126", "vehicle50127",
            "vehicle50128", "vehicle50129", "vehicle50130", "vehicle50131", "vehicle50132",
            "vehicle50133", "vehicle50134", "vehicle50135", "vehicle50136", "vehicle50137",
            "vehicle50138", "vehicle50139", "vehicle50140", "vehicle50141")

    val foreignVehicles = listOf<String>("vehicle20", "vehicle46", "vehicle54", "vehicle99", "vehicle108",
            "vehicle1", "vehicle2", "vehicle3", "vehicle4", "vehicle5",
            "vehicle6", "vehicle7", "vehicle8", "vehicle9", "vehicle10",
            "vehicle11", "vehicle12", "vehicle13", "vehicle14", "vehicle15",
            "vehicle16", "vehicle17", "vehicle18", "vehicle19", "vehicle21",
            "vehicle22", "vehicle23", "vehicle24", "vehicle25", "vehicle26",
            "vehicle27", "vehicle28", "vehicle29", "vehicle30", "vehicle31",
            "vehicle32", "vehicle33", "vehicle34", "vehicle35", "vehicle36",
            "vehicle37", "vehicle38", "vehicle39", "vehicle40", "vehicle41",
            "vehicle42", "vehicle43", "vehicle44", "vehicle45", "vehicle47",
            "vehicle48", "vehicle49", "vehicle50", "vehicle51", "vehicle52",
            "vehicle53", "vehicle55", "vehicle56", "vehicle57", "vehicle58",
            "vehicle59", "vehicle60", "vehicle61", "vehicle62", "vehicle63",
            "vehicle64", "vehicle65", "vehicle66", "vehicle67", "vehicle68",
            "vehicle69", "vehicle70", "vehicle71", "vehicle72", "vehicle73",
            "vehicle74", "vehicle75", "vehicle76", "vehicle77", "vehicle78",
            "vehicle79", "vehicle80", "vehicle81", "vehicle82", "vehicle83",
            "vehicle84", "vehicle85", "vehicle86", "vehicle87", "vehicle88",
            "vehicle89", "vehicle90", "vehicle91", "vehicle92", "vehicle93",
            "vehicle94", "vehicle95", "vehicle96", "vehicle97", "vehicle98",
            "vehicle100", "vehicle101", "vehicle102", "vehicle103", "vehicle104",
            "vehicle105", "vehicle106", "vehicle107", "vehicle109", "vehicle110",
            "vehicle111", "vehicle112", "vehicle113", "vehicle114", "vehicle115",
            "vehicle116", "vehicle117", "vehicle118", "vehicle119", "vehicle120",
            "vehicle121", "vehicle122", "vehicle123", "vehicle124", "vehicle125",
            "vehicle126", "vehicle127", "vehicle128", "vehicle129", "vehicle130",
            "vehicle131", "vehicle132", "vehicle133", "vehicle134", "vehicle135",
            "vehicle136")

    @Test
    fun chinaVehicle() {
        val gson = Gson()
        val type = object : TypeToken<Array<VehicleBean>>() {

        }.getType()
        val input = BufferedReader(InputStreamReader(context.getAssets().open("国产车.xml")))
        val list = gson.fromJson<Array<VehicleBean>>(input, type).map { it.imageId }

        for (v in list) {
            if (v !in chinaVehicles) {
                Log.d("SurvaryVehicleTest", v)
            }
        }
        Log.d("SurvaryVehicleTest", "华丽的分隔线")
        for (v in chinaVehicles) {
            if (v !in list) {
                Log.d("SurvaryVehicleTest", v)
            }
        }
    }

    @Test
    fun foreignVehicle() {
        val gson = Gson()
        val type = object : TypeToken<Array<VehicleBean>>() {

        }.getType()
        val input = BufferedReader(InputStreamReader(context.getAssets().open("外国车.xml")))
        val list = gson.fromJson<Array<VehicleBean>>(input, type).map { it.imageId }

        for (v in list) {
            if (v !in foreignVehicles) {
                Log.d("SurvaryVehicleTest", v)
            }

        }
        Log.d("SurvaryVehicleTest", "华丽的分隔线")

        for (v in foreignVehicles) {
            if (v !in list) {
                Log.d("SurvaryVehicleTest", v)
            }
        }


    }
}