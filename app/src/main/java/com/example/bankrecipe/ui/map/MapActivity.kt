package com.example.bankrecipe.ui.map

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.bankrecipe.MainActivity
import com.example.bankrecipe.R
import com.example.bankrecipe.Utils.FBAuth
import com.example.bankrecipe.databinding.ActivityMainBinding
import com.example.bankrecipe.databinding.ActivityMapBinding
import com.example.bankrecipe.ui.community.CommunityWrite
import com.google.android.gms.location.*
import com.google.firebase.annotations.concurrent.UiThread
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import java.io.IOException
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MapActivity : AppCompatActivity(),  OnMapReadyCallback {
    val permission_request = 99
    var splitAddress = "구"
    private lateinit var binding: ActivityMapBinding
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    lateinit var key : String
    private lateinit var name: String
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource : FusedLocationSource
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

   // var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
       storage = FirebaseStorage.getInstance()
       firestore=FirebaseFirestore.getInstance()
       binding.btnConfirm.setOnClickListener{
           val mkey = FBAuth.getUid()
           //위치 알아내기
          val intent = Intent(this, MainActivity::class.java)
           intent.putExtra("addresskey",mkey)
           var map = MapData(splitAddress,mkey)
           firestore.collection("map").document(mkey).set(map).addOnSuccessListener {
               finish()
           }

           Log.d("현재위치주소 : ", splitAddress)
           startActivity(intent)
       }

       startProcess()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        Log.d("onRequest", "onRequestPermissionsResult")

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Log.d("권한 거부", "권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            else {
                Log.d("권한 승인", "권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun startProcess(){
        //네이버 맵 동적으로 불러오기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            } //권한
        mapFragment.getMapAsync(this)
    } //권한이 있다면 onMapReady연결

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.minZoom = 5.0 //최소 줌
        val northWest = LatLng(31.43, 122.37) //서북단
        val southEast = LatLng(44.35, 132.0) //동남단
        naverMap.extent = LatLngBounds(northWest, southEast) //지도 영역을 국내 위주로 축소

        naverMap.locationSource = locationSource

        val marker = Marker()
        marker.position = LatLng(
            naverMap.cameraPosition.target.latitude,
            naverMap.cameraPosition.target.longitude
        )
        marker.icon = OverlayImage.fromResource(R.drawable.placeholder)
        marker.width = 120
        marker.height = 130
        marker.map = naverMap
        naverMap.addOnCameraChangeListener { reason, animated ->
            Log.i("NaverMap", "카메라 변경 - reson: $reason, animated: $animated")
            marker.position = LatLng(
                // 현재 보이는 네이버맵의 정중앙 가운데로 마커 이동
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            )

            binding.tvLocation.run {
                text = "위치 이동 중"
                setTextColor(Color.parseColor("#c4c4c4"))
            }
            binding.btnConfirm.run {
                setBackgroundResource(R.drawable.roundrect_background)
                setTextColor(Color.parseColor("#ffffff"))
                isEnabled = false
            }
        }
        naverMap.addOnCameraIdleListener {
            marker.position = LatLng(
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            )
            // 좌표 -> 주소 변환 텍스트 세팅, 버튼 활성화
            binding.tvLocation.run {
                text = getAddress(
                    naverMap.cameraPosition.target.latitude,
                    naverMap.cameraPosition.target.longitude
                )
                setTextColor(Color.parseColor("#2d2d2d"))
            }
            binding.btnConfirm.run {
                setBackgroundResource(R.drawable.roundrect_background)
                setTextColor(Color.parseColor("#FF000000"))
                isEnabled = true
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        //사용자 현재위치 받아오기
        var currentLocation: Location?
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                currentLocation = location
                // 위치 오버레이의 가시성은 기본적으로 false로 지정되어 있습니다. 가시성을 true로 변경하면 지도에 위치 오버레이가 나타납니다.
                // 파랑색 점, 현재 위치 표시
                naverMap.locationOverlay.run {
                    isVisible = true
                    position = LatLng(37.5670135, 126.9783740)
                }
                // 카메라 현재위치로 이동
                /*val cameraUpdate = CameraUpdate.scrollTo(
                    LatLng(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude
                    )
                )*/
                //naverMap.moveCamera(cameraUpdate)
                // 빨간색 마커 현재위치로 변경
                marker.position = LatLng(
                    naverMap.cameraPosition.target.latitude,
                    naverMap.cameraPosition.target.longitude
                )

            }
    }
    private fun getAddress(lat: Double, lng: Double): String {
        val geoCoder = Geocoder(this, Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."

        try {
            //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
            //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                // 주소 받아오기
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                addressResult = currentLocationAddress
                Log.d("현재위치주소 : ", addressResult)
                val array = address[0].getAddressLine(0).split(" ")[2]
                splitAddress = array
                Log.d("현재위치주소구 : ", splitAddress)

            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressResult
    }
    //내 위치를 가져오는 코드

    //lateinit: 나중에 초기화 해주겠다는 의미



}