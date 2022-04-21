package skw.interview.weatherforecast.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationProvider @Inject constructor(@ApplicationContext private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getLastLocation(): Location? {
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) ||
            isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            return fusedLocationClient.lastLocation.await()
        }

        return null
    }

    private fun isPermissionGranted(permissionName: String) =
        ActivityCompat.checkSelfPermission(
            context,
            permissionName
        ) == PackageManager.PERMISSION_GRANTED
}