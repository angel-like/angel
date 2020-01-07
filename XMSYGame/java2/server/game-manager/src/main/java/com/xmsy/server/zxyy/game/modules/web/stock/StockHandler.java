package com.xmsy.server.zxyy.game.modules.web.stock;

import com.xmsy.server.zxyy.game.cache.LocalContentCache;
import com.xmsy.server.zxyy.game.common.utils.MathUtil;
import com.xmsy.server.zxyy.game.common.utils.R;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.entity.GameStockEntity;
import com.xmsy.server.zxyy.game.modules.manager.gamestock.service.GameStockService;
import com.xmsy.server.zxyy.game.modules.web.stock.param.StockCache;
import com.xmsy.server.zxyy.game.modules.web.stock.param.StockParam;
import com.xmsy.server.zxyy.game.modules.web.stock.param.StockResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author adu
 * @email xxxxx
 * @date 2019-01-31 14:05:45
 */
@RestController
@RequestMapping("web/stock")
@Slf4j
public class StockHandler {

	@Autowired
	private LocalContentCache localContentCache;
	@Autowired
	private GameStockService gameStockService;
	private static final String GAMESTOCKKEY= "gameserver:gamestock:";
	private static final Long interval = (long) (5*60);//5分钟

	/**
	 *
	 */
	@GetMapping("/get")
	public R get(@RequestParam("roomId") String roomId) throws Exception {
		GameStockEntity gameStock = gameStockService.getGameStock(roomId);
		if(gameStock ==  null) {
			return R.error("没有对应的配置 roomId："+roomId);
		}
		if(null == gameStock.getEnable() || !gameStock.getEnable()) {
			return R.error("对应的配置已禁用 roomId："+roomId);
		}
		StockResult data = calculate(0l,0l,gameStock,BigDecimal.ZERO);
		return R.ok().put("data", data);
	}

	/**
	 *
	 */
	@PostMapping("/update")
	public R update(@RequestBody StockParam param) {
		log.info("StockParam: {}",param);
		GameStockEntity gameStock = gameStockService.getGameStock(param.getRoomId()+"");
		if(gameStock ==  null) {
			return R.error("没有对应的配置 roomId："+param.getRoomId());
		}
		if(null == gameStock.getEnable() || !gameStock.getEnable()) {
			return R.error("对应的配置已禁用 roomId："+param.getRoomId());
		}
		GameStockEntity updateGameStock = new GameStockEntity();
		BigDecimal tax = BigDecimal.ZERO;
		updateGameStock.setStock(MathUtil.getBigDecimal(param.getStock()));
		if(updateGameStock.getStock().compareTo(BigDecimal.ZERO)>0) {
			tax=updateGameStock.getStock().multiply(gameStock.getTaxRate());
		}
		StockResult data = calculate(param.getCount(),param.getStock(),gameStock,tax);

		updateGameStock.setId(gameStock.getId());
		updateGameStock.setTax(tax);
		updateGameStock.setStock(updateGameStock.getStock().subtract(tax));
		updateGameStock.setStockLimit(data.getTotal());
		log.info("updateGameStock: {}",updateGameStock);
		gameStockService.updateStock(updateGameStock);
		return R.ok().put("data", data);
	}


	private StockResult calculate(Long count, Long stockDiff, GameStockEntity gameStock, BigDecimal tax ) {
		StockResult result = new StockResult();
		StockCache cache = new StockCache();

		if(localContentCache.getVal(GAMESTOCKKEY+gameStock.getId())!=null) {
			cache = (StockCache) localContentCache.getVal(GAMESTOCKKEY+gameStock.getId());
		}
		cache.setTotal(gameStock.getStockLimit());
		cache.setStock(gameStock.getStock());
		log.info("gameStock: {}",gameStock);
		log.info("cache 1: {}",cache);
		Long time = cache.getTime();
		List<Long> history = new ArrayList<Long>();
		if(cache.getHistory() !=null) {
			history = cache.getHistory();
		}
		long now = new Date().getTime()/1000;
		if(null == time || time+interval*10 <now) {
			time = now;
			history = new ArrayList<Long>();
			history.add(0l);
			history.add(0l);
			history.add(0l);
			cache.setCount(0l);
		}
		if(null != time && time+interval <now) {
			time += interval;
			history.add(count);
			history.remove(0);
			cache.setCount(0l);
		}
		Long countTotal = cache.getCount()==null ? 0L : cache.getCount();
		countTotal+=count;
		BigDecimal total = BigDecimal.ZERO;
		int index=0;
		for (Long data:history) {
			BigDecimal val= MathUtil.getBigDecimal(data).multiply(MathUtil.getBigDecimal((index+1) * 2))
					.divide(MathUtil.getBigDecimal(1+history.size()),2,BigDecimal.ROUND_HALF_UP);
			total = total.add(val);
		}
		result.setHistory(total);
		cache.setTime(time);
		time = Math.max(now-time, 30);
		total=total.add(MathUtil.getBigDecimal(countTotal).multiply(MathUtil.getBigDecimal(interval/time)));
		total=total.multiply(MathUtil.getBigDecimal(5));

		result.setTime(time);
//	    limit := math.Min(total, this.total)
		BigDecimal limit=total;
		if(limit.compareTo(cache.getTotal())>0) {
			limit=cache.getTotal();
		}
		BigDecimal limitOne = cache.getLimit().multiply(MathUtil.getBigDecimal(2))
		.divide(MathUtil.getBigDecimal(2),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal limitTwo = cache.getLimit().multiply(MathUtil.getBigDecimal(1.5));
		if(limit.compareTo(limitOne)<0 ||
				limitTwo.compareTo(limit)<0	) {
			cache.setLimit(limit);
			cache.setValue(limit.multiply(cache.getStock()).
					divide(cache.getTotal(),2,BigDecimal.ROUND_HALF_UP));
		}
		cache.setCount(countTotal);
		//加上库存差减去抽税
		cache.setHistory(history);
		cache.setValue(MathUtil.getBigDecimal(stockDiff).add(cache.getValue()).subtract(tax));
		cache.setStock(MathUtil.getBigDecimal(stockDiff).add(cache.getStock()).subtract(tax).setScale(0,BigDecimal.ROUND_HALF_UP));
		localContentCache.setVal(GAMESTOCKKEY+gameStock.getId(), cache);
		result.setCount(cache.getCount());
		result.setStock(cache.getStock());
		result.setValue(cache.getValue());
		result.setTotal(cache.getTotal());
		result.setLimit(cache.getLimit());
		log.info("cache 2: {}",cache);
		log.info("result : {}",result);
		return result;
	}
}
