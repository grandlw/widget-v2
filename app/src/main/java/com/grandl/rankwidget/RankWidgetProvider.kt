package com.grandl.rankwidget
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
class RankWidgetProvider:AppWidgetProvider(){
 override fun onUpdate(c:Context,m:AppWidgetManager,ids:IntArray){ids.forEach{update(c,m,it)}}
 override fun onAppWidgetOptionsChanged(c:Context,m:AppWidgetManager,id:Int,o:Bundle){update(c,m,id)}
 companion object{
  fun updateAll(c:Context){val m=AppWidgetManager.getInstance(c);val cn=ComponentName(c,RankWidgetProvider::class.java);m.getAppWidgetIds(cn).forEach{update(c,m,it)}}
  private fun update(c:Context,m:AppWidgetManager,id:Int){val p=c.getSharedPreferences("rank",Context.MODE_PRIVATE);val tier=p.getString("tier","Platinum")!!;val d=p.getInt("delta",0);val v=RemoteViews(c.packageName,R.layout.rank_widget);val bg=when(tier.lowercase()){
"gold"->R.drawable.widget_gold;"platinum"->R.drawable.widget_platinum;"emerald"->R.drawable.widget_emerald;"diamond"->R.drawable.widget_diamond;"master"->R.drawable.widget_master;"grandmaster"->R.drawable.widget_grandmaster;"challenger"->R.drawable.widget_challenger;else->R.drawable.widget_neutral};v.setInt(R.id.widgetRoot,"setBackgroundResource",bg);v.setTextViewText(R.id.wRank,"$tier ${p.getString("div","1")}");v.setTextViewText(R.id.wLp,"${p.getInt("lp",30)} LP");v.setTextViewText(R.id.wRecord,"${p.getInt("wins",68)}W • ${p.getInt("losses",55)}L • %${p.getInt("wr",55)}");v.setTextViewText(R.id.wDelta,"Bugün ${if(d>=0) "+" else ""}$d LP");RankRepository.fetchEmblem(tier)?.let{v.setImageViewBitmap(R.id.wBadge,it)};m.updateAppWidget(id,v)}
 }
}
