package com.example.githubuserapp.background

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.githubuserapp.R
import com.example.githubuserapp.preference.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val ID_REPEATING = 100
        const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "Reminder channel"
        const val NOTIF_TITLE_ENGLISH = "Daily app check"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") setReminder(context)
        showReminderNotification(context)
    }

    private fun showReminderNotification(context: Context) {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val title = NOTIF_TITLE_ENGLISH
        val message = simpleDateFormat.format(SharedPrefManager(context).getHourAndMinute())
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_black_24)
            .setContentTitle(title)
            .setContentText(message.toString())
            .setSound(alarmSound)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManagerCompat.createNotificationChannel(channel)
        }

        notificationManagerCompat.notify(ID_REPEATING, builder.build())
    }

    fun setReminder(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val cal = getCalendar(context) as Calendar
        val receiver = ComponentName(context, AlarmReceiver::class.java)

        enableReceiver(context, receiver)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    fun cancelReminder(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiver = ComponentName(context, AlarmReceiver::class.java)

        disableReceiver(context, receiver)
        alarmManager.cancel(pendingIntent)
    }

    private fun enableReceiver(context: Context, receiver: ComponentName) {
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun disableReceiver(context: Context, receiver: ComponentName) {
        context.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun getCalendar(context: Context): Any {
        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(
                Calendar.HOUR_OF_DAY, SharedPrefManager(
                    context
                ).getHour())
            set(
                Calendar.MINUTE, SharedPrefManager(
                    context
                ).getMinute())
        }
    }

}