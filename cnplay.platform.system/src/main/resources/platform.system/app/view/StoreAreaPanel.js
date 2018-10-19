/*
 * File: app/view/StoreAreaPanel.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('platform.system.view.StoreAreaPanel', {
    extend: 'Ext.panel.Panel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.tree.Panel',
        'Ext.tree.View',
        'Ext.tree.Column'
    ],

    layout: 'border',
    title: '库房区域',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 'icon-refresh',
                            text: '刷新',
                            listeners: {
                                click: {
                                    fn: me.onRefreshClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-add',
                            text: '新增',
                            listeners: {
                                click: {
                                    fn: me.onAddClick,
                                    scope: me
                                },
                                afterrender: {
                                    fn: me.onAddAfterRender,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-edit',
                            text: '修改',
                            listeners: {
                                click: {
                                    fn: me.onEditClick,
                                    scope: me
                                },
                                afterrender: {
                                    fn: me.onEditAfterRender,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-del',
                            text: '删除',
                            listeners: {
                                click: {
                                    fn: me.onDelClick,
                                    scope: me
                                },
                                afterrender: {
                                    fn: me.onDelAfterRender,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'treepanel',
                    region: 'center',
                    border: false,
                    title: '库房区域树',
                    forceFit: true,
                    rootVisible: false,
                    viewConfig: {

                    },
                    columns: [
                        {
                            xtype: 'treecolumn',
                            dataIndex: 'name',
                            text: '区域名称'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'code',
                            text: '区域编码'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'memo',
                            text: '备注'
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onTreePanelAfterRender,
                            single: true,
                            scope: me
                        },
                        itemclick: {
                            fn: me.onTreepanelItemClick,
                            scope: me
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    onRefreshClick: function(button, e, eOpts) {
        this.loadTreeGrid();
    },

    onAddClick: function(button, e, eOpts) {
        this.showForm('');
    },

    onAddAfterRender: function(component, eOpts) {
        Common.hidden({params : {url:'/store/area/save'},component:component});
    },

    onEditClick: function(button, e, eOpts) {
          var  me =  this.treeGrid;
          var selected = me.getSelectionModel().selected;
        if(selected.items.length >1)
        {
            Common.setLoading({comp:me,msg:'只允许选择一个机构进行修改！'});
            return;
        }
        else
        {
            var record = selected.items[0];
            if(!Ext.isEmpty(record))
            {
                this.showForm(record.data.id);
            }
            else
            {
                Common.setLoading({comp:me,msg:'请选择要修改的机构！'});
            }

        }
    },

    onEditAfterRender: function(component, eOpts) {
        Common.hidden({params : {url:'/store/area/save'},component:component});
    },

    onDelClick: function(button, e, eOpts) {
        //Common.deleteSelectionIds(this.treeGrid,ctxp+'/system/organization/remove');

        try{
                var me = this;
                var selected = me.treeGrid.getSelectionModel().selected;
                var selecteditems = selected.items;
                if (selecteditems.length === 0)
                {
                    Ext.Msg.show(
                    {
                        title : "操作提示",
                        msg : "请选择要删除的区域!",
                        buttons : Ext.Msg.OK,
                        icon : Ext.Msg.WARNING
                    });
                    return;
                }
                var ids = [];
                Ext.each(selecteditems, function()
                {
                    var nd = this;
                    ids.push(nd.data.id);
                });
                Ext.Msg.confirm("确认提示", "确定要删除选中的区域吗？", function(button)
                {
                    if (button == "yes")
        				{
        					try
        					{
        						Common.ajax({
        							component : me.treeGrid,
        							params : {
        								'id' : ids.join(",")
        							},
        							message : '正在删除选中的区域...',
        							url : ctxp+'/store/area/remove',
        							callback : function(result)
        							{
                                        me.loadTreeGrid();
                                        //me.expandSelected();
                                        Common.setLoading({comp:me,msg:'区域删除成功！'});
        							}
        						});
        					}
        					catch (error)
        					{
        						Common.show({title:'操作提示',html:error.toString()});
        					}
        				}
        		});
                 }
        		catch (error)
        		{
        			Common.show({title:'操作提示',html:error.toString()});
        		}

    },

    onDelAfterRender: function(component, eOpts) {
         Common.hidden({params : {url:'/store/area/remove'},component:component});
    },

    onTreePanelAfterRender: function(component, eOpts) {
        this.treeGrid = component;
        try{

            this.loadTreeGrid();}
        catch(e){

            alert(e.toString());
        }

    },

    onTreepanelItemClick: function(dataview, record, item, index, e, eOpts) {
        Common.onTreeChildNodesChecked(dataview.node,false);
        record.set('checked', true);

        var items = this.treeGrid.getSelectionModel().selected.items;
        if(items.length > 0) {
            this.selectedParentNode = items[0].parentNode;
        }else{
            this.selectedParentNode = me.treeGrid.getRootNode();
        }
    },

    loadTreeGrid: function() {
        var me = this;
        me.expandNode = true;
        Common.bindTree({
            treePanel:me.treeGrid,
            url:ctxp + '/store/area/list',
            pid:'',
            fields:['id','sort','code','name','levelCode','memo'],
            onload:function onload(treestore, node, records, successful, eOpts){
                if(records.length>0 && me.expandNode){
                    Ext.defer(function(){me.treeGrid.expandNode(records[0]);},100);
                    me.expandNode = false;
                }
            }
        });
    },

    showForm: function(id) {
        try
        {
            var me = this;
            var formwin = Ext.create('platform.system.view.StoreAreaWindow');
            formwin.addListener('close', function(panel,opts)
                                {
                                    me.loadTreeGrid();
                                    //me.expandSelected();
                                });
            formwin.show();
            formwin.loadForm(id);
        }
        catch(error)
        {
            Common.show({title:'信息提示',html:error.toString()});
        }
    },

    check: function() {

        //用于防止重复提交
        excel_flag ++;
        if(excel_flag > 30)
        {
            //清空定时器
            window.clearInterval(win_check);
            //启用按钮
            exportExcelBtn.enable();
        }
        Ext.Ajax.request(
            {
                url : 'check',
                success : function (response, result)
                {
                    if(response.responseText=="true")
                    {
                        //清空定时器
                        window.clearInterval(win_check);
                        //启用按钮
                        exportExcelBtn.enable();
                    }
                }
            });

    },

    expandSelected: function() {
        try{
            var me = this;
            me.treeGrid.getStore().load({
                node: me.selectedParentNode,
                callback: function ()
                {
                    try{
                    me.treeGrid.expandNode(me.selectedParentNode);
                    }catch(Ex){}
                }
            });
        }catch(ee){}
    }

});