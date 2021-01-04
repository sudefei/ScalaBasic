select deviceid, from_unixtime(cast(substring(time,0,10) as bigint),'yyyy_MM_dd HH:mm:ss') time
      ,concat_ws('',collect_set(ac_x))     `ac_x`
      ,concat_ws('',collect_set(ac_y))   `ac_y`
      ,concat_ws('',collect_set(ac_z))   `ac_z`
      ,concat_ws('',collect_set(an_x)) `an_x`
	    ,concat_ws('',collect_set(an_y)) `an_y`
	    ,concat_ws('',collect_set(an_z)) `an_z`
      ,concat_ws('',collect_set(ac_max)) `ac_max`
	    ,concat_ws('',collect_set(an_max)) `an_max`
	    ,concat_ws('',collect_set(ac_pf)) `ac_pf`
	    ,concat_ws('',collect_set(an_pf)) `an_pf`
from
  (  select deviceid,time
           ,case when tagtype='ac_x' then value else '' end as  ac_x
           ,case when tagtype='ac_y' then value else '' end as  ac_y
           ,case when tagtype='ac_z' then value else '' end as  ac_z
           ,case when tagtype='an_x' then value else '' end as  an_x
		       ,case when tagtype='an_y' then value else '' end as  an_y
           ,case when tagtype='an_z' then value else '' end as  an_z
           ,case when tagtype='ac_max' then value else '' end as  ac_max
		       ,case when tagtype='an_max' then value else '' end as  an_max
           ,case when tagtype='ac_pf' then value else '' end as  ac_pf
           ,case when tagtype='an_pf' then value else '' end as  an_pf
      from stacker_z001_col where deviceid='ZDCG0001'
   ) a
 group by deviceId,time

insert overwrite local directory '/data/ZCG001.txt'
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
select deviceid,time
      ,concat_ws('',collect_set(ac_x))     `ac_x`
      ,concat_ws('',collect_set(ac_y))   `ac_y`
      ,concat_ws('',collect_set(ac_z))   `ac_z`
      ,concat_ws('',collect_set(an_x)) `an_x`
	  ,concat_ws('',collect_set(an_y)) `an_y`
	  ,concat_ws('',collect_set(an_z)) `an_z`
      ,concat_ws('',collect_set(ac_max)) `ac_max`
	  ,concat_ws('',collect_set(an_max)) `an_max`
	  ,concat_ws('',collect_set(ac_pf)) `ac_pf`
	  ,concat_ws('',collect_set(an_pf)) `an_pf`
from
  (  select deviceid,time
           ,case when tagtype='ac_x' then value else '' end as  ac_x
           ,case when tagtype='ac_y' then value else '' end as  ac_y
           ,case when tagtype='ac_z' then value else '' end as  ac_z
           ,case when tagtype='an_x' then value else '' end as  an_x
		   ,case when tagtype='an_y' then value else '' end as  an_y
           ,case when tagtype='an_z' then value else '' end as  an_z
           ,case when tagtype='ac_max' then value else '' end as  ac_max
		   ,case when tagtype='an_max' then value else '' end as  an_max
           ,case when tagtype='ac_pf' then value else '' end as  ac_pf
           ,case when tagtype='an_pf' then value else '' end as  an_pf
      from stacker_z001_col where deviceid='ZDCG0001'
   ) a
 group by deviceId,time